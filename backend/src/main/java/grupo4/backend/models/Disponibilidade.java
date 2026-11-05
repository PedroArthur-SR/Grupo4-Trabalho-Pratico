package grupo4.backend.models;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalSaude profissional;

    protected Disponibilidade() {}

    public Disponibilidade(DayOfWeek diaSemana, LocalTime horaInicio,
                           LocalTime horaFim) {
        if (diaSemana == null || horaInicio == null || horaFim == null) {
            throw new IllegalArgumentException(
                "Dia e horários são obrigatórios."
            );
        }

        if (!horaInicio.isBefore(horaFim)) {
            throw new IllegalArgumentException(
                "O horário inicial deve ser anterior ao horário final."
            );
        }

        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public boolean contem(LocalTime horario) {
        return horario != null
            && !horario.isBefore(horaInicio)
            && horario.isBefore(horaFim);
    }

    public int getId() {
        return id;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public ProfissionalSaude getProfissional() {
        return profissional;
    }

    public void setProfissional(ProfissionalSaude profissional) {
        this.profissional = profissional;
    }
}