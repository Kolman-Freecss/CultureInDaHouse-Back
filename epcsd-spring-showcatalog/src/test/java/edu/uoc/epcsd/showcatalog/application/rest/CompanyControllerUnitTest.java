package edu.uoc.epcsd.showcatalog.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uoc.epcsd.showcatalog.application.request.CreateCompanyRequest;
import edu.uoc.epcsd.showcatalog.domain.Company;
import edu.uoc.epcsd.showcatalog.domain.service.CompanyService;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataCategoryRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataCompanyRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.SpringDataShowRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Clase de test de controller ShowRESTController.
 *
 * @author Grupo Slack Chronicles
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CompanyRESTController.class)
public class CompanyControllerUnitTest
{
    /** Nombre de la empresa "Teatro Apolo". */
    private static final String COMPANY_NAME = "Teatro Apolo";

    /** Path de la llamada api rest a empresas por nombre. */
    private static final String REST_COMPANIES_NAME_PATH = "/api/companies/name";

    /** Path de la llamada api rest a crear empresa. */
    private static final String REST_COMPANIES_ADD_PATH = "/api/companies";

    /** Mock mvc. */
    @Autowired
    private MockMvc mockMvc;

    /** Mock del servicio de company. */
    @MockBean
    private CompanyService companyService;

    /** Mock del repositorio spring para acto. */
    @MockBean
    private SpringDataShowRepository springDataShowRepository;

    /** Mock del repositorio spring para categoria. */
    @MockBean
    private SpringDataCategoryRepository springDataCategoryRepository;

    /** Mock del repositorio spring para empresa. */
    @MockBean
    private SpringDataCompanyRepository springDataCompanyRepository;

    /**
     * Test que obtiene las empresas por nombre.
     */
    @Test
    void find_companies_like_name() throws Exception {
        log.info("Test: find_companies_like_name()");
        Company company = Company.builder().name(COMPANY_NAME).build();
        List<Company> companies = Arrays.asList(company);
        Mockito.when(companyService.findCompanyLikeName(COMPANY_NAME)).thenReturn(companies);
        mockMvc.perform(get(REST_COMPANIES_NAME_PATH).contentType(MediaType.APPLICATION_JSON).param("name", COMPANY_NAME)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is(COMPANY_NAME)));
    }

    /**
     * Test que crea una empresa.
     */
    @Test
    void add_company() throws Exception {
        log.info("Test: add_company()");
        final Long companyId = 40L;
        Company company = Company.builder().name(COMPANY_NAME).build();
        Mockito.when(companyService.createCompany(company)).thenReturn(companyId);

        final CreateCompanyRequest companyRequest = new CreateCompanyRequest(company);
        final ObjectMapper mapper = new ObjectMapper();
        String companyJson = mapper.writeValueAsString(companyRequest);

        mockMvc.perform(post(REST_COMPANIES_ADD_PATH).contentType(MediaType.APPLICATION_JSON).content(companyJson)).andDo(print()).andExpect(status().isCreated());
    }
}
