package edu.miu.sa.reservation.repository;

import edu.miu.sa.reservation.entity.Reservation;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends CassandraRepository<Reservation, UUID> {

}
