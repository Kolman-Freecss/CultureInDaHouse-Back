package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

public interface DomainTranslatable<T> {

    T toDomain();

}
