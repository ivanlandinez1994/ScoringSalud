package com.pf.scoringsalud.api.infraestructura;

import java.util.ArrayList;
import java.util.List;

public interface ArrayListReportValueCallback {
    void onSuccess(ArrayList<Reporte> value);
    void onFailure();
}
