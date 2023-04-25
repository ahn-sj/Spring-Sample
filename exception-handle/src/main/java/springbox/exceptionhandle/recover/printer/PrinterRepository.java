package springbox.exceptionhandle.recover.printer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PrinterRepository extends JpaRepository<Printer, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select p from Printer p where p.id = :id")
    Optional<Printer> findByIdWithLock(@Param("id") Long id);
}
