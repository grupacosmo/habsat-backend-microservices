package pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "post not found")
public class PostNotFoundException extends InternalException {
    public PostNotFoundException() {
        super(Code.POST_NOT_FOUND);
    }
}
