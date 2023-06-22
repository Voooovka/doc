package ua.lviv.iot.bank.model;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "balance")
    private Long balance;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = true)
    private Client owner;

    public Card(String number, LocalDate expiredDate, String cvv, Long balance, Client owner) {
        this.number = number;
        this.expiredDate = expiredDate;
        this.cvv = cvv;
        this.balance = balance;
        this.owner = owner;
    }

    public String[] toCsvFormat() {
        String[] record = { "CARD", this.number, this.expiredDate.toString(), this.cvv, this.balance.toString(), this.owner.getId().toString() };
        return record;
    }
}
