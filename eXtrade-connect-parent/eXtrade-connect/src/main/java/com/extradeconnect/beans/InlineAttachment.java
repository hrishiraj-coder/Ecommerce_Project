package com.extradeconnect.beans;

import com.extradeconnect.beans.Attachment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "of")
public class InlineAttachment extends Attachment {
    private String cid;
}
