package in.kestone.eventbuddy.model.agenda_model;

public class AgendaList {
    private static ModelAgenda agenda;

    public static ModelAgenda getAgenda() {
        return agenda;
    }

    public static void setAgenda(ModelAgenda agenda) {
        AgendaList.agenda = agenda;
    }
}
