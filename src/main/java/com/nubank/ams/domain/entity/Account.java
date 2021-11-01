package com.nubank.ams.domain.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Entity
@Table(name = "accounts")

@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Data
//@EqualsAndHashCode
@Getter
@Setter
@ToString

public class Account implements java.io.Serializable {

	private static final long serialVersionUID = 1001L;

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_generator")
	@SequenceGenerator(name = "accounts_generator", sequenceName = "acc_seq", initialValue = 1000)
	@Column(name = "acc_id")
	private Long id;

	@NonNull
	@Column(name = "acc_no", unique = true)
	private String accNumber;

	private double balance;

	@ManyToMany(mappedBy = "accounts", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@ToString.Exclude
	// @JsonBackReference
	@JsonIgnoreProperties("accounts")
	Set<Customer> customers; // = new HashSet< >();

	public void addCustomer(Customer customer) {
		if (null == getCustomers()) {
			setCustomers(new HashSet<>());
		}
		this.getCustomers().add(customer);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;

		// return Objects.hash(customers, id);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Account other = (Account) obj;

		return Objects.equals(id, other.getId());

		// return Objects.equals(customers, other.getCustomers())
		// && Objects.equals(id, other.getId());

	}

}
