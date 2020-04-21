
package com.suye.service;


import com.suye.consts.Protocol;
import com.suye.dto.Session;

/**
 * RegistryService
 * @author zxy
 */
public interface RegistryService{


    void  registerSession(Session session);

    void  unRegisterSession(Session session);

    String lookUpSessionServer(String sessionId);

    Protocol lookUpServerProtocol(String addr);


    String getLocalServerAddr();

    Protocol getLocalServerPro();

    String lookProtocolServer(Protocol protocol);
}
