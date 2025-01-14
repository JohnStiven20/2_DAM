package pizzeria.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pago", discriminatorType = DiscriminatorType.STRING)
public abstract class Pagable {

  public Pagable() {

  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;


  @OneToOne(mappedBy = "pagable")
  Pedido pedido;

  public abstract int pagar();
}

enum Pago {
  EFECTIVO,
  TARJETA
}