package name.isergius.android.task.hazpro.samplevideochat.core.socket;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import name.isergius.android.task.hazpro.samplevideochat.core.ClientStore;
import name.isergius.android.task.hazpro.samplevideochat.data.SDescription;

import static org.junit.Assert.*;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class EventSdpAnswerTest {
    @Mock
    private ClientStore clientStore;

    private EventSdpAnswer eventSdpAnswer;

    private JSONObject arg;
    private String JSON = "{\"clientId\": \"6236007a-6651-453b-8701-fe127541e048\",\"message\": {\"type\": \"answer\",\"sdp\": \"v=0\\r\\no=- 6617622604917392094 2 IN IP4 127.0.0.1\\r\\ns=-\\r\\nt=0 0\\r\\na=group:BUNDLE audio video\\r\\na=msid-semantic: WMS APPEAR\\r\\nm=audio 37695 UDP/TLS/RTP/SAVPF 111 103 9 0 8 106 105 13 126\\r\\nc=IN IP4 192.168.0.20\\r\\na=rtcp:9 IN IP4 0.0.0.0\\r\\na=candidate:316526476 1 udp 2122260223 192.168.0.20 37695 typ host generation 0\\r\\na=ice-ufrag:kj133sZ6FYpcqG2E\\r\\na=ice-pwd:NnAqz3kLBRpiS7Qn3dhB6DV7\\r\\na=fingerprint:sha-256 8B:19:ED:FD:59:36:5F:FA:90:20:0E:9C:E4:3E:7F:18:89:E1:02:3D:A8:47:F6:69:D4:18:CB:84:0A:31:9D:79\\r\\na=setup:active\\r\\na=mid:audio\\r\\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\\r\\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\\r\\na=sendrecv\\r\\na=rtcp-mux\\r\\na=rtpmap:111 opus/48000/2\\r\\na=fmtp:111 minptime=10; useinbandfec=1\\r\\na=rtpmap:103 ISAC/16000\\r\\na=rtpmap:9 G722/8000\\r\\na=rtpmap:0 PCMU/8000\\r\\na=rtpmap:8 PCMA/8000\\r\\na=rtpmap:106 CN/32000\\r\\na=rtpmap:105 CN/16000\\r\\na=rtpmap:13 CN/8000\\r\\na=rtpmap:126 telephone-event/8000\\r\\na=maxptime:60\\r\\na=ssrc:4067376651 cname:FvuFvIOQLOqZY4/A\\r\\na=ssrc:4067376651 msid:APPEAR APPEARa0\\r\\na=ssrc:4067376651 mslabel:APPEAR\\r\\na=ssrc:4067376651 label:APPEARa0\\r\\nm=video 9 UDP/TLS/RTP/SAVPF 100 101 116 117 96\\r\\nc=IN IP4 0.0.0.0\\r\\na=rtcp:9 IN IP4 0.0.0.0\\r\\na=ice-ufrag:kj133sZ6FYpcqG2E\\r\\na=ice-pwd:NnAqz3kLBRpiS7Qn3dhB6DV7\\r\\na=fingerprint:sha-256 8B:19:ED:FD:59:36:5F:FA:90:20:0E:9C:E4:3E:7F:18:89:E1:02:3D:A8:47:F6:69:D4:18:CB:84:0A:31:9D:79\\r\\na=setup:active\\r\\na=mid:video\\r\\na=extmap:2 urn:ietf:params:rtp-hdrext:toffset\\r\\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\\r\\na=extmap:4 urn:3gpp:video-orientation\\r\\na=sendrecv\\r\\na=rtcp-mux\\r\\na=rtpmap:100 VP8/90000\\r\\na=rtcp-fb:100 ccm fir\\r\\na=rtcp-fb:100 nack\\r\\na=rtcp-fb:100 nack pli\\r\\na=rtcp-fb:100 goog-remb\\r\\na=rtcp-fb:100 transport-cc\\r\\na=rtpmap:101 VP9/90000\\r\\na=rtcp-fb:101 ccm fir\\r\\na=rtcp-fb:101 nack\\r\\na=rtcp-fb:101 nack pli\\r\\na=rtcp-fb:101 goog-remb\\r\\na=rtcp-fb:101 transport-cc\\r\\na=rtpmap:116 red/90000\\r\\na=rtpmap:117 ulpfec/90000\\r\\na=rtpmap:96 rtx/90000\\r\\na=fmtp:96 apt=100\\r\\na=ssrc-group:FID 4133508253 1165410135\\r\\na=ssrc:4133508253 cname:FvuFvIOQLOqZY4/A\\r\\na=ssrc:4133508253 msid:APPEAR APPEARv0\\r\\na=ssrc:4133508253 mslabel:APPEAR\\r\\na=ssrc:4133508253 label:APPEARv0\\r\\na=ssrc:1165410135 cname:FvuFvIOQLOqZY4/A\\r\\na=ssrc:1165410135 msid:APPEAR APPEARv0\\r\\na=ssrc:1165410135 mslabel:APPEAR\\r\\na=ssrc:1165410135 label:APPEARv0\\r\\n\"}}";

    @Before
    public void setUp() throws Exception {
        arg = new JSONObject(JSON);
        eventSdpAnswer = new EventSdpAnswer(clientStore);
    }

    @Test
    public void call() throws Exception {
        String expectedClientId = "6236007a-6651-453b-8701-fe127541e048";
        SDescription expectedSDescription = new SDescription("answer", "v=0\r\no=- 6617622604917392094 2 IN IP4 127.0.0.1\r\ns=-\r\nt=0 0\r\na=group:BUNDLE audio video\r\na=msid-semantic: WMS APPEAR\r\nm=audio 37695 UDP/TLS/RTP/SAVPF 111 103 9 0 8 106 105 13 126\r\nc=IN IP4 192.168.0.20\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=candidate:316526476 1 udp 2122260223 192.168.0.20 37695 typ host generation 0\r\na=ice-ufrag:kj133sZ6FYpcqG2E\r\na=ice-pwd:NnAqz3kLBRpiS7Qn3dhB6DV7\r\na=fingerprint:sha-256 8B:19:ED:FD:59:36:5F:FA:90:20:0E:9C:E4:3E:7F:18:89:E1:02:3D:A8:47:F6:69:D4:18:CB:84:0A:31:9D:79\r\na=setup:active\r\na=mid:audio\r\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\r\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=sendrecv\r\na=rtcp-mux\r\na=rtpmap:111 opus/48000/2\r\na=fmtp:111 minptime=10; useinbandfec=1\r\na=rtpmap:103 ISAC/16000\r\na=rtpmap:9 G722/8000\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:106 CN/32000\r\na=rtpmap:105 CN/16000\r\na=rtpmap:13 CN/8000\r\na=rtpmap:126 telephone-event/8000\r\na=maxptime:60\r\na=ssrc:4067376651 cname:FvuFvIOQLOqZY4/A\r\na=ssrc:4067376651 msid:APPEAR APPEARa0\r\na=ssrc:4067376651 mslabel:APPEAR\r\na=ssrc:4067376651 label:APPEARa0\r\nm=video 9 UDP/TLS/RTP/SAVPF 100 101 116 117 96\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:kj133sZ6FYpcqG2E\r\na=ice-pwd:NnAqz3kLBRpiS7Qn3dhB6DV7\r\na=fingerprint:sha-256 8B:19:ED:FD:59:36:5F:FA:90:20:0E:9C:E4:3E:7F:18:89:E1:02:3D:A8:47:F6:69:D4:18:CB:84:0A:31:9D:79\r\na=setup:active\r\na=mid:video\r\na=extmap:2 urn:ietf:params:rtp-hdrext:toffset\r\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:4 urn:3gpp:video-orientation\r\na=sendrecv\r\na=rtcp-mux\r\na=rtpmap:100 VP8/90000\r\na=rtcp-fb:100 ccm fir\r\na=rtcp-fb:100 nack\r\na=rtcp-fb:100 nack pli\r\na=rtcp-fb:100 goog-remb\r\na=rtcp-fb:100 transport-cc\r\na=rtpmap:101 VP9/90000\r\na=rtcp-fb:101 ccm fir\r\na=rtcp-fb:101 nack\r\na=rtcp-fb:101 nack pli\r\na=rtcp-fb:101 goog-remb\r\na=rtcp-fb:101 transport-cc\r\na=rtpmap:116 red/90000\r\na=rtpmap:117 ulpfec/90000\r\na=rtpmap:96 rtx/90000\r\na=fmtp:96 apt=100\r\na=ssrc-group:FID 4133508253 1165410135\r\na=ssrc:4133508253 cname:FvuFvIOQLOqZY4/A\r\na=ssrc:4133508253 msid:APPEAR APPEARv0\r\na=ssrc:4133508253 mslabel:APPEAR\r\na=ssrc:4133508253 label:APPEARv0\r\na=ssrc:1165410135 cname:FvuFvIOQLOqZY4/A\r\na=ssrc:1165410135 msid:APPEAR APPEARv0\r\na=ssrc:1165410135 mslabel:APPEAR\r\na=ssrc:1165410135 label:APPEARv0\r\n");
        eventSdpAnswer.call(arg);
        Mockito.verify(clientStore).addSDescription(expectedClientId, expectedSDescription);
    }

}