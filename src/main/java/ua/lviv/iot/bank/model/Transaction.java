package ua.lviv.iot.bank.model;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paid_amount")
    private String paidAmount;

    @Column(name = "fee")
    private String fee;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private Client recipient;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Client sender;

    public Transaction(String paidAmount, String fee, LocalDate date, Client recipient, Client sender) {
        this.paidAmount = paidAmount;
        this.fee = fee;
        this.date = date;
        this.recipient = recipient;
        this.sender = sender;
    }

    public String[] toCsvFormat() {
        String[] record = { "TRANSACTION", this.paidAmount, this.fee, this.date.toString(), this.recipient.getId().toString(), this.sender.getId().toString() };
        return record;
    }
}
