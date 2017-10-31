package za.co.ajk.incidentman.web.rest;

import com.codahale.metrics.annotation.Timed;
import za.co.ajk.incidentman.domain.Member;

import za.co.ajk.incidentman.repository.MemberRepository;
import za.co.ajk.incidentman.web.rest.errors.BadRequestAlertException;
import za.co.ajk.incidentman.web.rest.util.HeaderUtil;
import za.co.ajk.incidentman.web.rest.util.PaginationUtil;
import za.co.ajk.incidentman.service.dto.MemberDTO;
import za.co.ajk.incidentman.service.mapper.MemberMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Member.
 */
@RestController
@RequestMapping("/api")
public class MemberResource {

    private final Logger log = LoggerFactory.getLogger(MemberResource.class);

    private static final String ENTITY_NAME = "member";

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberResource(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    /**
     * POST  /members : Create a new member.
     *
     * @param memberDTO the memberDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memberDTO, or with status 400 (Bad Request) if the member has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/members")
    @Timed
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) throws URISyntaxException {
        log.debug("REST request to save Member : {}", memberDTO);
        if (memberDTO.getId() != null) {
            throw new BadRequestAlertException("A new member cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Member member = memberMapper.toEntity(memberDTO);
        member = memberRepository.save(member);
        MemberDTO result = memberMapper.toDto(member);
        return ResponseEntity.created(new URI("/api/members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /members : Updates an existing member.
     *
     * @param memberDTO the memberDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memberDTO,
     * or with status 400 (Bad Request) if the memberDTO is not valid,
     * or with status 500 (Internal Server Error) if the memberDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/members")
    @Timed
    public ResponseEntity<MemberDTO> updateMember(@Valid @RequestBody MemberDTO memberDTO) throws URISyntaxException {
        log.debug("REST request to update Member : {}", memberDTO);
        if (memberDTO.getId() == null) {
            return createMember(memberDTO);
        }
        Member member = memberMapper.toEntity(memberDTO);
        member = memberRepository.save(member);
        MemberDTO result = memberMapper.toDto(member);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, memberDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /members : get all the members.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of members in body
     */
    @GetMapping("/members")
    @Timed
    public ResponseEntity<List<MemberDTO>> getAllMembers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Members");
        Page<Member> page = memberRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/members");
        return new ResponseEntity<>(memberMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /members/:id : get the "id" member.
     *
     * @param id the id of the memberDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memberDTO, or with status 404 (Not Found)
     */
    @GetMapping("/members/{id}")
    @Timed
    public ResponseEntity<MemberDTO> getMember(@PathVariable String id) {
        log.debug("REST request to get Member : {}", id);
        Member member = memberRepository.findOne(id);
        MemberDTO memberDTO = memberMapper.toDto(member);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(memberDTO));
    }

    /**
     * DELETE  /members/:id : delete the "id" member.
     *
     * @param id the id of the memberDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/members/{id}")
    @Timed
    public ResponseEntity<Void> deleteMember(@PathVariable String id) {
        log.debug("REST request to delete Member : {}", id);
        memberRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
