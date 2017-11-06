package za.co.ajk.incidentman.web.rest;

import za.co.ajk.incidentman.EventModuleApp;

import za.co.ajk.incidentman.domain.Member;
import za.co.ajk.incidentman.repository.MemberRepository;
import za.co.ajk.incidentman.service.dto.MemberDTO;
import za.co.ajk.incidentman.service.mapper.MemberMapper;
import za.co.ajk.incidentman.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static za.co.ajk.incidentman.web.rest.TestUtil.sameInstant;
import static za.co.ajk.incidentman.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventModuleApp.class)
public class MemberResourceIntTest {

    private static final String DEFAULT_MEMBER_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_SURNAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMemberMockMvc;

    private Member member;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberResource memberResource = new MemberResource(memberRepository, memberMapper);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity() {
        Member member = new Member()
            .memberKey(DEFAULT_MEMBER_KEY)
            .memberName(DEFAULT_MEMBER_NAME)
            .memberSurname(DEFAULT_MEMBER_SURNAME)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .updatedBy(DEFAULT_UPDATED_BY);
        return member;
    }

    @Before
    public void initTest() {
        memberRepository.deleteAll();
        member = createEntity();
    }

    @Test
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getMemberKey()).isEqualTo(DEFAULT_MEMBER_KEY);
        assertThat(testMember.getMemberName()).isEqualTo(DEFAULT_MEMBER_NAME);
        assertThat(testMember.getMemberSurname()).isEqualTo(DEFAULT_MEMBER_SURNAME);
        assertThat(testMember.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testMember.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testMember.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    public void createMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member with an existing ID
        member.setId("existing_id");
        MemberDTO memberDTO = memberMapper.toDto(member);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkMemberKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setMemberKey(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMemberNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setMemberName(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMemberSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setMemberSurname(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setDateCreated(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setDateModified(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.save(member);

        // Get all the memberList
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId())))
            .andExpect(jsonPath("$.[*].memberKey").value(hasItem(DEFAULT_MEMBER_KEY.toString())))
            .andExpect(jsonPath("$.[*].memberName").value(hasItem(DEFAULT_MEMBER_NAME.toString())))
            .andExpect(jsonPath("$.[*].memberSurname").value(hasItem(DEFAULT_MEMBER_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(sameInstant(DEFAULT_DATE_CREATED))))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(sameInstant(DEFAULT_DATE_MODIFIED))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.save(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId()))
            .andExpect(jsonPath("$.memberKey").value(DEFAULT_MEMBER_KEY.toString()))
            .andExpect(jsonPath("$.memberName").value(DEFAULT_MEMBER_NAME.toString()))
            .andExpect(jsonPath("$.memberSurname").value(DEFAULT_MEMBER_SURNAME.toString()))
            .andExpect(jsonPath("$.dateCreated").value(sameInstant(DEFAULT_DATE_CREATED)))
            .andExpect(jsonPath("$.dateModified").value(sameInstant(DEFAULT_DATE_MODIFIED)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMember() throws Exception {
        // Initialize the database
        memberRepository.save(member);
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findOne(member.getId());
        updatedMember
            .memberKey(UPDATED_MEMBER_KEY)
            .memberName(UPDATED_MEMBER_NAME)
            .memberSurname(UPDATED_MEMBER_SURNAME)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .updatedBy(UPDATED_UPDATED_BY);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getMemberKey()).isEqualTo(UPDATED_MEMBER_KEY);
        assertThat(testMember.getMemberName()).isEqualTo(UPDATED_MEMBER_NAME);
        assertThat(testMember.getMemberSurname()).isEqualTo(UPDATED_MEMBER_SURNAME);
        assertThat(testMember.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testMember.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testMember.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    public void updateNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.save(member);
        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Get the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
        Member member1 = new Member();
        member1.setId("id1");
        Member member2 = new Member();
        member2.setId(member1.getId());
        assertThat(member1).isEqualTo(member2);
        member2.setId("id2");
        assertThat(member1).isNotEqualTo(member2);
        member1.setId(null);
        assertThat(member1).isNotEqualTo(member2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberDTO.class);
        MemberDTO memberDTO1 = new MemberDTO();
        memberDTO1.setId("id1");
        MemberDTO memberDTO2 = new MemberDTO();
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
        memberDTO2.setId(memberDTO1.getId());
        assertThat(memberDTO1).isEqualTo(memberDTO2);
        memberDTO2.setId("id2");
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
        memberDTO1.setId(null);
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
    }
}
