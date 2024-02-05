package sync.slamtalk.chat.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO implements Serializable {
    // 채팅방 고유 아이디
    private String roomId;
    // 채팅방 상대방 아이디
    private String partnerId;
    // 채팅방 이름
    private String name;
    // 채팅방 마지막 메세지
    private String last_message;


    // 채팅방 마지막 메세지 업데이트
    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }
}
