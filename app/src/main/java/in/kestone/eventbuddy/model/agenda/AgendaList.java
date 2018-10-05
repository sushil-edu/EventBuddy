package in.kestone.eventbuddy.model.agenda;

public class AgendaList {
    static ModelAgenda agenda;

    public static ModelAgenda getAgenda() {
        return agenda;
    }

    public static void setAgenda(ModelAgenda agenda) {
        AgendaList.agenda = agenda;
    }
}
