package com.nubank.ams.domain.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "customers")


@NoArgsConstructor
@AllArgsConstructor
@Builder 
//@Data
//@EqualsAndHashCode
@Getter
@Setter
@ToString

public class Customer implements java.io.Serializable {

	private static final long serialVersionUID = 1002L;

	@Id

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
	@SequenceGenerator(name = "customer_generator", sequenceName = "cust_seq", initialValue = 100)
	@Column(name = "cust_id")
	private Long id;

	@NonNull
	private String name;

	@NonNull
	@Column(unique = true)
	// @Email(message = "invalid format")
	private String email;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "customer_accounts", joinColumns = @JoinColumn(name = "cust_id"), inverseJoinColumns = @JoinColumn(name = "acc_id"))
	@ToString.Exclude
	// @JsonManagedReference
	Set<Account> accounts;

	public void addAccount(Account account) {
		if (null == getAccounts()) {
			setAccounts(new HashSet<>());
		}
		this.getAccounts().add(account);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;

		// return Objects.hash(accounts, id);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Customer other = (Customer) obj;

		return Objects.equals(id, other.getId());
	}

}
