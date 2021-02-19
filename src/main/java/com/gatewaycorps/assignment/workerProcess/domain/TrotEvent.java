package com.gatewaycorps.assignment.workerProcess.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gatewaycorps.assignment.workerProcess.validators.TrotEventConstraint;
import java.util.Objects;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TrotEventConstraint
public class TrotEvent {

    public String event;
    @Valid
    public Horse horse;
    public Long time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrotEvent trotEvent = (TrotEvent) o;
        return horse.equals(trotEvent.horse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(horse);
    }
}
