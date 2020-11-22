package com.pf.scoringsalud.api.infraestructura;

import com.pf.scoringsalud.user.Domain.User;

public interface UserValueCallback {
    void onSuccess(User value);
    void onFailure();
}
