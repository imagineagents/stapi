package com.cezarykluczynski.stapi.model.character.entity;

import com.cezarykluczynski.stapi.model.common.entity.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.Gender;
import com.cezarykluczynski.stapi.model.common.entity.MaritalStatus;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.google.common.collect.Sets;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = {"performers"})
public class Character extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_sequence_generator")
	@SequenceGenerator(name = "character_sequence_generator", sequenceName = "character_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private Integer yearOfBirth;

	private Integer monthOfBirth;

	private Integer dayOfBirth;

	private String placeOfBirth;

	private Integer yearOfDeath;

	private Integer monthOfDeath;

	private Integer dayOfDeath;

	private String placeOfDeath;

	private Integer height;

	private Integer weight;

	private Boolean deceased;

	@Enumerated(EnumType.STRING)
	private BloodType bloodType;

	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;

	private String serialNumber;

	@ManyToMany(mappedBy = "characters")
	private Set<Performer> performers = Sets.newHashSet();

}