package sync.slamtalk.notification.listener.team;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import sync.slamtalk.common.Site;
import sync.slamtalk.notification.NotificationSender;
import sync.slamtalk.notification.dto.request.NotificationRequest;
import sync.slamtalk.notification.model.NotificationType;
import sync.slamtalk.notification.util.StringSlicer;
import sync.slamtalk.team.entity.TeamMatching;
import sync.slamtalk.team.event.TeamMatchingPostDeletionEvent;

@Component
@RequiredArgsConstructor
public class TeamMatchingDeleteNotifier {
    private final NotificationSender notificationSender;

    private static final String MATE_POST_DELETION_REJECTION_MESSAGE =  "상대팀 찾기 %s의 모집이 취소되었습니다.";


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(TeamMatchingPostDeletionEvent.class)
    public void acceptMateSupport(TeamMatchingPostDeletionEvent event) {

        TeamMatching teamMatching = event.teamMatching();

        // 알림 요청 객체를 생성합니다.
        NotificationRequest request = NotificationRequest.of(
                String.format(MATE_POST_DELETION_REJECTION_MESSAGE, StringSlicer.slice(teamMatching.getTitle())),
                Site.teamMatching(teamMatching.getTeamMatchingId()),
                event.participantUserIds(),
                teamMatching.getWriter().getId(),
                NotificationType.TEAM
        );

        // 알림을 전송합니다.
        notificationSender.send(request);
    }
}
