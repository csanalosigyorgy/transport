package com.webuni.transport.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.webuni.transport.dto.LoginDto;
import com.webuni.transport.dto.TransportPlanDelayDto;
import com.webuni.transport.model.Address;
import com.webuni.transport.model.Milestone;
import com.webuni.transport.model.MilestoneType;
import com.webuni.transport.model.Section;
import com.webuni.transport.model.TransportPlan;
import com.webuni.transport.model.TransportUser;
import com.webuni.transport.repository.AddressRepository;
import com.webuni.transport.repository.MilestoneRepository;
import com.webuni.transport.repository.SectionRepository;
import com.webuni.transport.repository.TransportPlanRepository;
import com.webuni.transport.repository.TransportUserRepository;
import com.webuni.transport.service.util.ErrorEntity;

import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class TransportPlanServiceIT {

	private static final String USERNAME = "fekete_eszter";
	private static final String PASSWORD = "pass";
	private static final Set<String> ROLES = Set.of("TransportManager");

	private static final String BASE_URI = "/api/transport-plans/{id}/delay";

	private String jwtToken;

	@Autowired
	private WebTestClient client;

	@Autowired
	private TransportUserRepository transportUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private MilestoneRepository milestoneRepository;

	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private TransportPlanRepository transportPlanRepository;

	@BeforeEach
	public void init(){
		if(transportUserRepository.findUserByUsername(USERNAME).isEmpty()){
			transportUserRepository.addUser(new TransportUser(USERNAME, passwordEncoder.encode(PASSWORD), ROLES));
		}
		jwtToken = client
				.post()
				.uri("/api/login")
				.bodyValue(new LoginDto(USERNAME, PASSWORD))
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.returnResult()
				.getResponseBody();
	}

	private TransportPlan saveFullTransportPlan(){
		Address address = addressRepository.save(new Address(0L, "HU", "Budapest", "Ezredes utca", "1024", "11", null, null));

		TransportPlan transportPlan = transportPlanRepository.save(new TransportPlan(0L, 1000, null));

		Milestone milestone1 = milestoneRepository.save(new Milestone(0L, MilestoneType.FROM, address, LocalDateTime.of(2022, 1, 1, 13, 10)));
		Milestone milestone2 = milestoneRepository.save(new Milestone(0L, MilestoneType.TO, address, LocalDateTime.of(2022, 1, 1, 15, 10)));
		Milestone milestone3 = milestoneRepository.save(new Milestone(0L, MilestoneType.FROM, address, LocalDateTime.of(2022, 2, 1, 10, 10)));
		Milestone milestone4 = milestoneRepository.save(new Milestone(0L, MilestoneType.TO, address, LocalDateTime.of(2022, 2, 1, 12, 0)));

		Section section1 = sectionRepository.save(new Section(0L, 1, milestone1, milestone2, transportPlan));
		transportPlan.addSection(section1);

		Section section2 = sectionRepository.save(new Section(0L, 2, milestone3, milestone4, transportPlan));
		transportPlan.addSection(section2);

		return transportPlan;
	}

	private TransportPlan saveTransportPlanWithoutSections(){
		return transportPlanRepository.save(new TransportPlan(0L, 2000, null));
	}

	@Test
	void save10MinutesDelayForFromMilestone_itListed() {
		int delayLength = 10;
		TransportPlan originalTransportPlan = saveFullTransportPlan();
		Section originalSection = originalTransportPlan.getSections().stream()
				.filter(m -> m.getNumber() == 1).findAny()
				.get();

		Milestone originalFromMilestone = originalSection.getFromMilestone();
		Milestone originalToMilestone = originalSection.getToMilestone();
		TransportPlanDelayDto delayDto = new TransportPlanDelayDto(originalFromMilestone.getId(), delayLength);

		saveDelay(originalTransportPlan.getId(), delayDto)
				.expectStatus()
				.isOk();

		Section updatedSection = sectionRepository.findById(originalSection.getId()).get();
		Milestone updatedFromMilestone = updatedSection.getFromMilestone();
		Milestone updatedToMilestone = updatedSection.getToMilestone();
		TransportPlan updatedTransportPlan = updatedSection.getTransportPlan();

		assertThat(updatedFromMilestone.getPlannedTime().getMinute()).isEqualTo(originalFromMilestone.getPlannedTime().getMinute() + delayLength);
		assertThat(updatedToMilestone.getPlannedTime().getMinute()).isEqualTo(originalToMilestone.getPlannedTime().getMinute() + delayLength);
		assertThat(updatedTransportPlan.getRevenue()).isEqualTo((originalTransportPlan.getRevenue() * 0.995));
	}

	@Test
	void save10MinutesDelayForToMilestone_itListed() {
		int delayLength = 10;
		TransportPlan originalTransportPlan = saveFullTransportPlan();
		Section originalSection = originalTransportPlan.getSections().stream()
				.filter(m -> m.getNumber() == 1).findAny()
				.get();
		Milestone originalToMilestone = originalSection.getToMilestone();

		Section originalNextSection = originalTransportPlan.getSections().stream()
				.filter(m -> m.getNumber() == 2).findAny()
				.get();
		Milestone originalNextFromMilestone = originalNextSection.getFromMilestone();

		TransportPlanDelayDto delayDto = new TransportPlanDelayDto(originalToMilestone.getId(), delayLength);

		saveDelay(originalTransportPlan.getId(), delayDto)
				.expectStatus()
				.isOk();

		Section updatedNextSection = sectionRepository.findById(originalNextSection.getId()).get();
		Milestone updatedFromMilestone = updatedNextSection.getFromMilestone();
		TransportPlan updatedTransportPlan = updatedNextSection.getTransportPlan();

		assertThat(updatedFromMilestone.getPlannedTime().getMinute()).isEqualTo(originalNextFromMilestone.getPlannedTime().getMinute() + delayLength);
		assertThat(updatedTransportPlan.getRevenue()).isEqualTo((originalTransportPlan.getRevenue() * 0.995));
	}

	@Test
	void saveDelayForMilestoneThatNotPartOfTheTransportPlan_getStatusNotFound() {
		int delayLength = 10;
		TransportPlan transportPlanWithSections = saveFullTransportPlan();
		TransportPlan transportPlanWithoutSections = saveTransportPlanWithoutSections();

		Section originalSection = transportPlanWithSections.getSections().stream()
				.filter(m -> m.getNumber() == 1).findAny()
				.get();

		Milestone originalFromMilestone = originalSection.getFromMilestone();
		TransportPlanDelayDto delayDto = new TransportPlanDelayDto(originalFromMilestone.getId(), delayLength);

		saveDelay(transportPlanWithoutSections.getId(), delayDto)
				.expectStatus()
				.isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void save10MinutesDelayForTheLastSectionToMilestone_getStatusNotFound() {
		int delayLength = 10;
		TransportPlan originalTransportPlan = saveFullTransportPlan();

		Section originalSection = originalTransportPlan.getSections().stream()
				.filter(m -> m.getNumber() == 2).findAny()
				.get();
		Milestone originalToMilestone = originalSection.getToMilestone();
		TransportPlanDelayDto delayDto = new TransportPlanDelayDto(originalToMilestone.getId(), delayLength);

		saveDelay(originalTransportPlan.getId(), delayDto)
				.expectStatus()
				.isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void save0MinutesDelayForAFromMilestone_nothingChange() {
		int delayLength = 0;
		TransportPlan originalTransportPlan = saveFullTransportPlan();
		Section originalSection = originalTransportPlan.getSections().stream()
				.filter(m -> m.getNumber() == 1).findAny()
				.get();

		Milestone originalFromMilestone = originalSection.getFromMilestone();
		Milestone originalToMilestone = originalSection.getToMilestone();
		TransportPlanDelayDto delayDto = new TransportPlanDelayDto(originalFromMilestone.getId(), delayLength);

		saveDelay(originalTransportPlan.getId(), delayDto)
				.expectStatus()
				.isOk();

		Section updatedSection = sectionRepository.findById(originalSection.getId()).get();
		Milestone updatedFromMilestone = updatedSection.getFromMilestone();
		Milestone updatedToMilestone = updatedSection.getToMilestone();
		TransportPlan updatedTransportPlan = updatedSection.getTransportPlan();

		assertThat(updatedFromMilestone.getPlannedTime().getMinute()).isEqualTo(originalFromMilestone.getPlannedTime().getMinute() + delayLength);
		assertThat(updatedToMilestone.getPlannedTime().getMinute()).isEqualTo(originalToMilestone.getPlannedTime().getMinute() + delayLength);
		assertThat(updatedTransportPlan.getRevenue()).isEqualTo((originalTransportPlan.getRevenue()));
	}

	private ResponseSpec saveDelay(Long transportPlanId, TransportPlanDelayDto delayDto){
		return client
				.post()
				.uri(BASE_URI, transportPlanId)
				.headers(headers -> headers.setBearerAuth(jwtToken))
				.bodyValue(delayDto)
				.exchange();
	}
}