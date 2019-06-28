package in.kestone.eventbuddy.model.agenda_model;

public class MasterAgenda {
    static ModelAgenda modelAgenda;

    public static ModelAgenda getModelAgenda() {
        return modelAgenda;
    }

    public static void setModelAgenda(ModelAgenda modelAgenda) {
        MasterAgenda.modelAgenda = modelAgenda;
    }
}
