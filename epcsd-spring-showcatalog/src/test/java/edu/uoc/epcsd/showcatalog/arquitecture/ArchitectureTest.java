package edu.uoc.epcsd.showcatalog.arquitecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import org.springframework.stereotype.Service;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * Clase de test de arquitectura con ArchUnit.
 * 
 * @author Grupo Slack Chronicles
 */
@AnalyzeClasses(packages = "edu.uoc.epcsd.showcatalog", importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class })
public class ArchitectureTest {

	/**
	 * Comprueba que los servicios estan anotados con @Service y que el nombre de la clase termine en ServiceImpl
	 */
	@ArchTest
	static final ArchRule domain_service_with_spring_annotation = classes().that().resideInAPackage("..domain.service..").and().areAnnotatedWith(Service.class).should()
			.haveSimpleNameEndingWith("ServiceImpl");

	/**
	 * Comprueba que cumple la arquitectura hexagonal
	 */
	@ArchTest
	static final ArchRule onion_architecture_is_respected = onionArchitecture().domainModels("..domain..").domainServices("..domain.service..").applicationServices("..application..")
			.adapter("cli", "..infrastructure.kafka..").adapter("persistence", "..infrastructure.repository..").adapter("rest", "..application.rest..");
}
