package pl.motokomando.healthcare.domain.miscellaneous;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.miscellaneous.DateAvailable;
import pl.motokomando.healthcare.domain.patients.appointments.AppointmentsRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MiscellaneousServiceImpl implements MiscellaneousService {

    private final AppointmentsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public DateAvailable checkDateAvailability(LocalDateTime date) {
        boolean available = repository.isDateAvailable(date);
        return new DateAvailable(date, available);
    }

}
