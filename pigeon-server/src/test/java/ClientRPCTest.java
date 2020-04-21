import com.alibaba.fastjson.JSON;
import com.suye.consts.Protocol;
import com.suye.dto.MessageDTO;
import com.suye.netty.RpcClientBootstrap;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * description
 *
 * @author zxy
 * create time 2020/3/27 17:17
 */


public class ClientRPCTest {

    @Test
    public void rpcClient() throws Exception {
        SocketChannel channel = RpcClientBootstrap.getchannel("192.168.202.151:8888", Protocol.WEBSOCKET);
        // DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/hello");

        while (true) {
            Thread.sleep(1000);
            if (channel.isActive()) {
                MessageDTO body = new MessageDTO();
                ByteBuffer buffer = ByteBuffer.wrap(JSON.toJSONBytes(body));
                TextWebSocketFrame frame = new TextWebSocketFrame();
                channel.writeAndFlush(frame).isSuccess();
            }

        }


    }


}
