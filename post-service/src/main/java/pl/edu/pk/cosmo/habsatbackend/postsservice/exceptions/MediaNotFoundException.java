package pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "media not found")
public class MediaNotFoundException extends InternalException {
    public MediaNotFoundException() {
        super(Code.MEDIA_NOT_FOUND);
    }
}
