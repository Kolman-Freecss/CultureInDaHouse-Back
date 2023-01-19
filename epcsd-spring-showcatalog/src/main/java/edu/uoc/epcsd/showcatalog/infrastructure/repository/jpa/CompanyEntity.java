package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.uoc.epcsd.showcatalog.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class CompanyEntity implements DomainTranslatable<Company> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = true, length = 128)
	private String address;
    
	@Column(name = "email", nullable = true, length = 254)
	private String email;
	
	@Column(name = "mobile_number", nullable = true, length = 9)
	private String mobileNumber;

    public static CompanyEntity fromDomain(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyEntity.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .email(company.getEmail())
                .mobileNumber(company.getMobileNumber())
                .build();
    }

    @Override
    public Company toDomain() {
        return Company.builder()
                .id(this.getId())
                .name(this.getName())
                .address(this.getAddress())
                .email(this.getEmail())
                .mobileNumber(this.getMobileNumber())
                .build();
    }
}
