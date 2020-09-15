package dk.dd.rmi.dbserver;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Customer
{
    @Id
    private Long accnum;
    @NonNull
    private String name;
    @NonNull
    private Double amount;

    public void setAccnum(Long accnum) {
        this.accnum = accnum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
