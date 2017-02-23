package name.isergius.android.task.hazpro.samplevideochat.core;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.socket.client.Socket;
import name.isergius.android.task.hazpro.samplevideochat.data.RoomConfig;
import name.isergius.android.task.hazpro.samplevideochat.data.Server;

/**
 * @author Sergey Kondratyev
 */

@RunWith(MockitoJUnitRunner.class)
public class WsMessageProducerTest {

    @Mock
    private Socket socket;

    private WsMessageProducer wsMessageProducer;

    @Before
    public void setUp() throws Exception {
        this.wsMessageProducer = new WsMessageProducer(socket);
    }
    //TODO
    @Ignore
    @Test
    public void sendJoinRoom() throws Exception {
        String MESSAGE_ID = "join_room";
        String JSON = "{\"roomName\":\"/t\",\"config\":{\"isAudioEnabled\":true,\"isVideoEnabled\":true}}";
        JSONObject expectedArg = new JSONObject(JSON);
        RoomConfig roomConfig = new RoomConfig("t", true, true);
        wsMessageProducer.sendJoinRoom(roomConfig);
        Mockito.verify(socket).emit(MESSAGE_ID, expectedArg);
    }
    //TODO
    @Ignore
    @Test
    public void sendIceCandidate() throws Exception {
        String MESSAGE_ID = "ice_candidate";
        String JSON = "{\"receiverId\": \"5746768e-ddda-4189-86e8-ac687cdc0de3\",\"message\": {\"candidate\": \"candidate:1511920713 1 udp 2122260223 192.168.0.2 58175 typ host generation 0 ufrag Qx1Z network-id 1\",\"sdpMid\": \"audio\",\"sdpMLineIndex\": 0}}";
        String receiverId = "5746768e-ddda-4189-86e8-ac687cdc0de3";
        JSONObject expectedArg = new JSONObject(JSON);
        IceCandidate iceCandidate = new IceCandidate("audio", 0, "candidate:1511920713 1 udp 2122260223 192.168.0.2 58175 typ host generation 0 ufrag Qx1Z network-id 1");
        wsMessageProducer.sendIceCandidate(receiverId, iceCandidate);
        Mockito.verify(socket).emit(MESSAGE_ID, expectedArg);
    }
    //TODO
    @Ignore
    @Test
    public void sendReadyToReceiveOffer() throws Exception {
        String MESSAGE_ID = "ready_to_receive_offer";
        String JSON = "{\"receiverId\": \"4de0ab73-18c8-453c-be05-8e22f7a08a3a\",\"iceServers\": {\"iceServers\": [{\"url\": \"turn:turn.appear.in:443?transport=udp\",\"urls\": \"turn:turn.appear.in:443?transport=udp\",\"username\": \"appearin:1487455545\",\"credential\": \"naOgcJfbFzzNfcfGAVySY8dwA28=\"},{\"url\": \"turn:turn.appear.in:443?transport=tcp\",\"urls\": \"turn:turn.appear.in:443?transport=tcp\",\"username\": \"appearin:1487455545\",\"credential\": \"naOgcJfbFzzNfcfGAVySY8dwA28=\"},{\"url\": \"turns:turn.appear.in:443?transport=tcp\",\"urls\": \"turns:turn.appear.in:443?transport=tcp\",\"username\": \"appearin:1487455545\",\"credential\": \"naOgcJfbFzzNfcfGAVySY8dwA28=\"}]}}";
        String receiverId = "4de0ab73-18c8-453c-be05-8e22f7a08a3a";
        JSONObject expectedArg = new JSONObject(JSON);
        Set<Server> servers = new HashSet<>();
        servers.add(new Server("naOgcJfbFzzNfcfGAVySY8dwA28=", "appearin:1487455545", "turn:turn.appear.in:443?transport=udp"));
        servers.add(new Server("naOgcJfbFzzNfcfGAVySY8dwA28=", "appearin:1487455545", "turn:turn.appear.in:443?transport=tcp"));
        servers.add(new Server("naOgcJfbFzzNfcfGAVySY8dwA28=", "appearin:1487455545", "turns:turn.appear.in:443?transport=tcp"));
        wsMessageProducer.sendReadyToReceiveOffer(receiverId, servers);
        Mockito.verify(socket).emit(MESSAGE_ID, expectedArg);
    }
    //TODO
    @Ignore
    @Test
    public void sendSpdAnswer() throws Exception {
        String MESSAGE_ID = "sdp_answer";
        String JSON = "{\"receiverId\": \"b5adbce8-5157-4c8b-8a18-f8993841343c\",\"message\": {\"type\": \"answer\",\"sdp\": \"v=0\\r\\no=- 2217718718549787764 2 IN IP4 127.0.0.1\\r\\ns=-\\r\\nt=0 0\\r\\na=group:BUNDLE audio video\\r\\na=msid-semantic: WMS 772dfc70-8047-4476-b60f-0bb86227a50f\\r\\nm=audio 9 UDP/TLS/RTP/SAVPF 111 103 9 0 8 106 105 13 126\\r\\nc=IN IP4 0.0.0.0\\r\\na=rtcp:9 IN IP4 0.0.0.0\\r\\na=ice-ufrag:AEd7\\r\\na=ice-pwd:8GD7zLZcIajJR/8DWQIlbnSk\\r\\na=fingerprint:sha-256 64:31:08:29:42:CF:7A:B5:0D:45:DA:53:B6:DD:57:59:96:BE:D2:32:7A:DE:6A:D2:DA:5D:B5:9D:D1:00:17:A6\\r\\na=setup:active\\r\\na=mid:audio\\r\\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\\r\\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\\r\\na=sendrecv\\r\\na=rtcp-mux\\r\\na=rtpmap:111 opus/48000/2\\r\\na=fmtp:111 minptime=10;useinbandfec=1\\r\\na=rtpmap:103 ISAC/16000\\r\\na=rtpmap:9 G722/8000\\r\\na=rtpmap:0 PCMU/8000\\r\\na=rtpmap:8 PCMA/8000\\r\\na=rtpmap:106 CN/32000\\r\\na=rtpmap:105 CN/16000\\r\\na=rtpmap:13 CN/8000\\r\\na=rtpmap:126 telephone-event/8000\\r\\na=ssrc:2078329215 cname:eHbg0R7zJXRWqGJg\\r\\na=ssrc:2078329215 msid:772dfc70-8047-4476-b60f-0bb86227a50f 6be50008-32f3-412d-b90b-66c84cb2461c\\r\\na=ssrc:2078329215 mslabel:772dfc70-8047-4476-b60f-0bb86227a50f\\r\\na=ssrc:2078329215 label:6be50008-32f3-412d-b90b-66c84cb2461c\\r\\nm=video 9 UDP/TLS/RTP/SAVPF 100 101 116 117 96\\r\\nc=IN IP4 0.0.0.0\\r\\na=rtcp:9 IN IP4 0.0.0.0\\r\\na=ice-ufrag:AEd7\\r\\na=ice-pwd:8GD7zLZcIajJR/8DWQIlbnSk\\r\\na=fingerprint:sha-256 64:31:08:29:42:CF:7A:B5:0D:45:DA:53:B6:DD:57:59:96:BE:D2:32:7A:DE:6A:D2:DA:5D:B5:9D:D1:00:17:A6\\r\\na=setup:active\\r\\na=mid:video\\r\\na=extmap:2 urn:ietf:params:rtp-hdrext:toffset\\r\\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\\r\\na=extmap:4 urn:3gpp:video-orientation\\r\\na=sendrecv\\r\\na=rtcp-mux\\r\\na=rtpmap:100 VP8/90000\\r\\na=rtcp-fb:100 ccm fir\\r\\na=rtcp-fb:100 nack\\r\\na=rtcp-fb:100 nack pli\\r\\na=rtcp-fb:100 goog-remb\\r\\na=rtcp-fb:100 transport-cc\\r\\na=rtpmap:101 VP9/90000\\r\\na=rtcp-fb:101 ccm fir\\r\\na=rtcp-fb:101 nack\\r\\na=rtcp-fb:101 nack pli\\r\\na=rtcp-fb:101 goog-remb\\r\\na=rtcp-fb:101 transport-cc\\r\\na=rtpmap:116 red/90000\\r\\na=rtpmap:117 ulpfec/90000\\r\\na=rtpmap:96 rtx/90000\\r\\na=fmtp:96 apt=100\\r\\na=ssrc-group:FID 1439325759 2871137784\\r\\na=ssrc:1439325759 cname:eHbg0R7zJXRWqGJg\\r\\na=ssrc:1439325759 msid:772dfc70-8047-4476-b60f-0bb86227a50f 8ac17353-6081-4c36-832d-3043501d6fc3\\r\\na=ssrc:1439325759 mslabel:772dfc70-8047-4476-b60f-0bb86227a50f\\r\\na=ssrc:1439325759 label:8ac17353-6081-4c36-832d-3043501d6fc3\\r\\na=ssrc:2871137784 cname:eHbg0R7zJXRWqGJg\\r\\na=ssrc:2871137784 msid:772dfc70-8047-4476-b60f-0bb86227a50f 8ac17353-6081-4c36-832d-3043501d6fc3\\r\\na=ssrc:2871137784 mslabel:772dfc70-8047-4476-b60f-0bb86227a50f\\r\\na=ssrc:2871137784 label:8ac17353-6081-4c36-832d-3043501d6fc3\\r\\n\"}}";
        String receiverId = "b5adbce8-5157-4c8b-8a18-f8993841343c";
        JSONObject expectedArg = new JSONObject(JSON);
        SessionDescription sessionDescription = new SessionDescription(SessionDescription.Type.ANSWER, "v=0\r\no=- 2217718718549787764 2 IN IP4 127.0.0.1\r\ns=-\r\nt=0 0\r\na=group:BUNDLE audio video\r\na=msid-semantic: WMS 772dfc70-8047-4476-b60f-0bb86227a50f\r\nm=audio 9 UDP/TLS/RTP/SAVPF 111 103 9 0 8 106 105 13 126\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:AEd7\r\na=ice-pwd:8GD7zLZcIajJR/8DWQIlbnSk\r\na=fingerprint:sha-256 64:31:08:29:42:CF:7A:B5:0D:45:DA:53:B6:DD:57:59:96:BE:D2:32:7A:DE:6A:D2:DA:5D:B5:9D:D1:00:17:A6\r\na=setup:active\r\na=mid:audio\r\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\r\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=sendrecv\r\na=rtcp-mux\r\na=rtpmap:111 opus/48000/2\r\na=fmtp:111 minptime=10;useinbandfec=1\r\na=rtpmap:103 ISAC/16000\r\na=rtpmap:9 G722/8000\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:106 CN/32000\r\na=rtpmap:105 CN/16000\r\na=rtpmap:13 CN/8000\r\na=rtpmap:126 telephone-event/8000\r\na=ssrc:2078329215 cname:eHbg0R7zJXRWqGJg\r\na=ssrc:2078329215 msid:772dfc70-8047-4476-b60f-0bb86227a50f 6be50008-32f3-412d-b90b-66c84cb2461c\r\na=ssrc:2078329215 mslabel:772dfc70-8047-4476-b60f-0bb86227a50f\r\na=ssrc:2078329215 label:6be50008-32f3-412d-b90b-66c84cb2461c\r\nm=video 9 UDP/TLS/RTP/SAVPF 100 101 116 117 96\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:AEd7\r\na=ice-pwd:8GD7zLZcIajJR/8DWQIlbnSk\r\na=fingerprint:sha-256 64:31:08:29:42:CF:7A:B5:0D:45:DA:53:B6:DD:57:59:96:BE:D2:32:7A:DE:6A:D2:DA:5D:B5:9D:D1:00:17:A6\r\na=setup:active\r\na=mid:video\r\na=extmap:2 urn:ietf:params:rtp-hdrext:toffset\r\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:4 urn:3gpp:video-orientation\r\na=sendrecv\r\na=rtcp-mux\r\na=rtpmap:100 VP8/90000\r\na=rtcp-fb:100 ccm fir\r\na=rtcp-fb:100 nack\r\na=rtcp-fb:100 nack pli\r\na=rtcp-fb:100 goog-remb\r\na=rtcp-fb:100 transport-cc\r\na=rtpmap:101 VP9/90000\r\na=rtcp-fb:101 ccm fir\r\na=rtcp-fb:101 nack\r\na=rtcp-fb:101 nack pli\r\na=rtcp-fb:101 goog-remb\r\na=rtcp-fb:101 transport-cc\r\na=rtpmap:116 red/90000\r\na=rtpmap:117 ulpfec/90000\r\na=rtpmap:96 rtx/90000\r\na=fmtp:96 apt=100\r\na=ssrc-group:FID 1439325759 2871137784\r\na=ssrc:1439325759 cname:eHbg0R7zJXRWqGJg\r\na=ssrc:1439325759 msid:772dfc70-8047-4476-b60f-0bb86227a50f 8ac17353-6081-4c36-832d-3043501d6fc3\r\na=ssrc:1439325759 mslabel:772dfc70-8047-4476-b60f-0bb86227a50f\r\na=ssrc:1439325759 label:8ac17353-6081-4c36-832d-3043501d6fc3\r\na=ssrc:2871137784 cname:eHbg0R7zJXRWqGJg\r\na=ssrc:2871137784 msid:772dfc70-8047-4476-b60f-0bb86227a50f 8ac17353-6081-4c36-832d-3043501d6fc3\r\na=ssrc:2871137784 mslabel:772dfc70-8047-4476-b60f-0bb86227a50f\r\na=ssrc:2871137784 label:8ac17353-6081-4c36-832d-3043501d6fc3\r\n");
        wsMessageProducer.sendSdpAnswer(receiverId, sessionDescription);
        Mockito.verify(socket).emit(MESSAGE_ID, expectedArg);
    }
    //TODO
    @Ignore
    @Test
    public void sendSpdOffer() throws Exception {
        String MESSAGE_ID = "sdp_offer";
        String JSON = "{\"receiverId\": \"5746768e-ddda-4189-86e8-ac687cdc0de3\",\"message\": {\"type\": \"offer\",\"sdp\": \"v=0\\r\\no=- 8269055939614302404 2 IN IP4 127.0.0.1\\r\\ns=-\\r\\nt=0 0\\r\\na=group:BUNDLE audio video\\r\\na=msid-semantic: WMS add997b2-7c7f-47ba-814d-16068a73b5e5\\r\\nm=audio 9 UDP/TLS/RTP/SAVPF 111 103 104 9 0 8 106 105 13 126\\r\\nc=IN IP4 0.0.0.0\\r\\na=rtcp:9 IN IP4 0.0.0.0\\r\\na=ice-ufrag:Qx1Z\\r\\na=ice-pwd:LYTFbQRcLFChIwQ8j5dFQ6ec\\r\\na=fingerprint:sha-256 36:A0:0F:94:59:3B:63:7B:96:54:68:FB:91:42:9E:FA:9A:AB:0D:EA:47:21:34:D6:30:43:01:7F:87:36:1D:07\\r\\na=setup:actpass\\r\\na=mid:audio\\r\\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\\r\\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\\r\\na=sendrecv\\r\\na=rtcp-mux\\r\\na=rtpmap:111 opus/48000/2\\r\\na=rtcp-fb:111 transport-cc\\r\\na=fmtp:111 minptime=10;useinbandfec=1\\r\\na=rtpmap:103 ISAC/16000\\r\\na=rtpmap:104 ISAC/32000\\r\\na=rtpmap:9 G722/8000\\r\\na=rtpmap:0 PCMU/8000\\r\\na=rtpmap:8 PCMA/8000\\r\\na=rtpmap:106 CN/32000\\r\\na=rtpmap:105 CN/16000\\r\\na=rtpmap:13 CN/8000\\r\\na=rtpmap:126 telephone-event/8000\\r\\na=ssrc:825124482 cname:eoQiP6ohZ/uVy5Jt\\r\\na=ssrc:825124482 msid:add997b2-7c7f-47ba-814d-16068a73b5e5 563b93db-f7e0-492d-8039-d7742ac34f9e\\r\\na=ssrc:825124482 mslabel:add997b2-7c7f-47ba-814d-16068a73b5e5\\r\\na=ssrc:825124482 label:563b93db-f7e0-492d-8039-d7742ac34f9e\\r\\nm=video 9 UDP/TLS/RTP/SAVPF 100 101 107 116 117 96 97 99 98\\r\\nc=IN IP4 0.0.0.0\\r\\na=rtcp:9 IN IP4 0.0.0.0\\r\\na=ice-ufrag:Qx1Z\\r\\na=ice-pwd:LYTFbQRcLFChIwQ8j5dFQ6ec\\r\\na=fingerprint:sha-256 36:A0:0F:94:59:3B:63:7B:96:54:68:FB:91:42:9E:FA:9A:AB:0D:EA:47:21:34:D6:30:43:01:7F:87:36:1D:07\\r\\na=setup:actpass\\r\\na=mid:video\\r\\na=extmap:2 urn:ietf:params:rtp-hdrext:toffset\\r\\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\\r\\na=extmap:4 urn:3gpp:video-orientation\\r\\na=extmap:5 http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01\\r\\na=extmap:6 http://www.webrtc.org/experiments/rtp-hdrext/playout-delay\\r\\na=sendrecv\\r\\na=rtcp-mux\\r\\na=rtcp-rsize\\r\\na=rtpmap:100 VP8/90000\\r\\na=rtcp-fb:100 ccm fir\\r\\na=rtcp-fb:100 nack\\r\\na=rtcp-fb:100 nack pli\\r\\na=rtcp-fb:100 goog-remb\\r\\na=rtcp-fb:100 transport-cc\\r\\na=rtpmap:101 VP9/90000\\r\\na=rtcp-fb:101 ccm fir\\r\\na=rtcp-fb:101 nack\\r\\na=rtcp-fb:101 nack pli\\r\\na=rtcp-fb:101 goog-remb\\r\\na=rtcp-fb:101 transport-cc\\r\\na=rtpmap:107 H264/90000\\r\\na=rtcp-fb:107 ccm fir\\r\\na=rtcp-fb:107 nack\\r\\na=rtcp-fb:107 nack pli\\r\\na=rtcp-fb:107 goog-remb\\r\\na=rtcp-fb:107 transport-cc\\r\\na=fmtp:107 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\\r\\na=rtpmap:116 red/90000\\r\\na=rtpmap:117 ulpfec/90000\\r\\na=rtpmap:96 rtx/90000\\r\\na=fmtp:96 apt=100\\r\\na=rtpmap:97 rtx/90000\\r\\na=fmtp:97 apt=101\\r\\na=rtpmap:99 rtx/90000\\r\\na=fmtp:99 apt=107\\r\\na=rtpmap:98 rtx/90000\\r\\na=fmtp:98 apt=116\\r\\na=ssrc-group:FID 1297985650 177210747\\r\\na=ssrc:1297985650 cname:eoQiP6ohZ/uVy5Jt\\r\\na=ssrc:1297985650 msid:add997b2-7c7f-47ba-814d-16068a73b5e5 0df7c612-4f8e-4bf5-bc57-89b246b7d1f9\\r\\na=ssrc:1297985650 mslabel:add997b2-7c7f-47ba-814d-16068a73b5e5\\r\\na=ssrc:1297985650 label:0df7c612-4f8e-4bf5-bc57-89b246b7d1f9\\r\\na=ssrc:177210747 cname:eoQiP6ohZ/uVy5Jt\\r\\na=ssrc:177210747 msid:add997b2-7c7f-47ba-814d-16068a73b5e5 0df7c612-4f8e-4bf5-bc57-89b246b7d1f9\\r\\na=ssrc:177210747 mslabel:add997b2-7c7f-47ba-814d-16068a73b5e5\\r\\na=ssrc:177210747 label:0df7c612-4f8e-4bf5-bc57-89b246b7d1f9\\r\\n\"}}";
        String receiverId = "5746768e-ddda-4189-86e8-ac687cdc0de3";
        JSONObject expectedArg = new JSONObject(JSON);
        SessionDescription sessionDescription = new SessionDescription(SessionDescription.Type.OFFER, "v=0\r\no=- 8269055939614302404 2 IN IP4 127.0.0.1\r\ns=-\r\nt=0 0\r\na=group:BUNDLE audio video\r\na=msid-semantic: WMS add997b2-7c7f-47ba-814d-16068a73b5e5\r\nm=audio 9 UDP/TLS/RTP/SAVPF 111 103 104 9 0 8 106 105 13 126\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:Qx1Z\r\na=ice-pwd:LYTFbQRcLFChIwQ8j5dFQ6ec\r\na=fingerprint:sha-256 36:A0:0F:94:59:3B:63:7B:96:54:68:FB:91:42:9E:FA:9A:AB:0D:EA:47:21:34:D6:30:43:01:7F:87:36:1D:07\r\na=setup:actpass\r\na=mid:audio\r\na=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level\r\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=sendrecv\r\na=rtcp-mux\r\na=rtpmap:111 opus/48000/2\r\na=rtcp-fb:111 transport-cc\r\na=fmtp:111 minptime=10;useinbandfec=1\r\na=rtpmap:103 ISAC/16000\r\na=rtpmap:104 ISAC/32000\r\na=rtpmap:9 G722/8000\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:106 CN/32000\r\na=rtpmap:105 CN/16000\r\na=rtpmap:13 CN/8000\r\na=rtpmap:126 telephone-event/8000\r\na=ssrc:825124482 cname:eoQiP6ohZ/uVy5Jt\r\na=ssrc:825124482 msid:add997b2-7c7f-47ba-814d-16068a73b5e5 563b93db-f7e0-492d-8039-d7742ac34f9e\r\na=ssrc:825124482 mslabel:add997b2-7c7f-47ba-814d-16068a73b5e5\r\na=ssrc:825124482 label:563b93db-f7e0-492d-8039-d7742ac34f9e\r\nm=video 9 UDP/TLS/RTP/SAVPF 100 101 107 116 117 96 97 99 98\r\nc=IN IP4 0.0.0.0\r\na=rtcp:9 IN IP4 0.0.0.0\r\na=ice-ufrag:Qx1Z\r\na=ice-pwd:LYTFbQRcLFChIwQ8j5dFQ6ec\r\na=fingerprint:sha-256 36:A0:0F:94:59:3B:63:7B:96:54:68:FB:91:42:9E:FA:9A:AB:0D:EA:47:21:34:D6:30:43:01:7F:87:36:1D:07\r\na=setup:actpass\r\na=mid:video\r\na=extmap:2 urn:ietf:params:rtp-hdrext:toffset\r\na=extmap:3 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time\r\na=extmap:4 urn:3gpp:video-orientation\r\na=extmap:5 http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01\r\na=extmap:6 http://www.webrtc.org/experiments/rtp-hdrext/playout-delay\r\na=sendrecv\r\na=rtcp-mux\r\na=rtcp-rsize\r\na=rtpmap:100 VP8/90000\r\na=rtcp-fb:100 ccm fir\r\na=rtcp-fb:100 nack\r\na=rtcp-fb:100 nack pli\r\na=rtcp-fb:100 goog-remb\r\na=rtcp-fb:100 transport-cc\r\na=rtpmap:101 VP9/90000\r\na=rtcp-fb:101 ccm fir\r\na=rtcp-fb:101 nack\r\na=rtcp-fb:101 nack pli\r\na=rtcp-fb:101 goog-remb\r\na=rtcp-fb:101 transport-cc\r\na=rtpmap:107 H264/90000\r\na=rtcp-fb:107 ccm fir\r\na=rtcp-fb:107 nack\r\na=rtcp-fb:107 nack pli\r\na=rtcp-fb:107 goog-remb\r\na=rtcp-fb:107 transport-cc\r\na=fmtp:107 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\r\na=rtpmap:116 red/90000\r\na=rtpmap:117 ulpfec/90000\r\na=rtpmap:96 rtx/90000\r\na=fmtp:96 apt=100\r\na=rtpmap:97 rtx/90000\r\na=fmtp:97 apt=101\r\na=rtpmap:99 rtx/90000\r\na=fmtp:99 apt=107\r\na=rtpmap:98 rtx/90000\r\na=fmtp:98 apt=116\r\na=ssrc-group:FID 1297985650 177210747\r\na=ssrc:1297985650 cname:eoQiP6ohZ/uVy5Jt\r\na=ssrc:1297985650 msid:add997b2-7c7f-47ba-814d-16068a73b5e5 0df7c612-4f8e-4bf5-bc57-89b246b7d1f9\r\na=ssrc:1297985650 mslabel:add997b2-7c7f-47ba-814d-16068a73b5e5\r\na=ssrc:1297985650 label:0df7c612-4f8e-4bf5-bc57-89b246b7d1f9\r\na=ssrc:177210747 cname:eoQiP6ohZ/uVy5Jt\r\na=ssrc:177210747 msid:add997b2-7c7f-47ba-814d-16068a73b5e5 0df7c612-4f8e-4bf5-bc57-89b246b7d1f9\r\na=ssrc:177210747 mslabel:add997b2-7c7f-47ba-814d-16068a73b5e5\r\na=ssrc:177210747 label:0df7c612-4f8e-4bf5-bc57-89b246b7d1f9\r\n");
        wsMessageProducer.sendSdpOffer(receiverId, sessionDescription);
        Mockito.verify(socket).emit(MESSAGE_ID, expectedArg);
    }

}