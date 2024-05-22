package com.extradeconnect.service;

import com.extradeconnect.beans.message.Message;

public interface MessageService {
    void dispatch(Message message);
}
