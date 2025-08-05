package com.emrekirdim.appointmentapp.Configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Doctor Appointment Booking API")
                        .version("1.0")
                        .description(
                                "This API provides a hospital appointment booking system. It allows administrators, doctors, and patients to interact with the platform in a structured way.\n\n" +
                                        "System Workflow:\n" +
                                        "1. Administrators register medical specialties.\n" +
                                        "2. Administrators register doctors and associate them with specialties.\n" +
                                        "3. Patients register in the system to create accounts.\n" +
                                        "4. Patients search for doctors by specialty and view their availability.\n" +
                                        "5. Patients book, cancel, and view their appointment history.\n" +
                                        "6. Doctors manage their schedules, mark appointments as completed, and record appointment results.\n" +
                                        "Important: You cannot add a doctor without first creating specialties. Similarly, patients cannot book appointments until doctors are registered."
                        )
                        .contact(new Contact()
                                .name("Emre Kirdim")
                                .email("emrekirdim@hotmail.com")
                                .url("https://github.com/deimooos"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .tags(Arrays.asList(
                        new Tag().name("Admin - Specialty Management")
                                .description("Operations for creating, updating, listing, and deleting medical specialties."),
                        new Tag().name("Admin - Doctor Management")
                                .description("Operations for creating, updating, listing, and deleting doctors, as well as retrieving doctors by specialty."),
                        new Tag().name("Patient - User Management")
                                .description("Operations for registering, updating, and managing patient accounts."),
                        new Tag().name("User Appointment Management")
                                .description("Endpoints for patients to manage their appointments: booking, cancelling, and viewing."),
                        new Tag().name("Doctor Appointment Management")
                                .description("Endpoints for doctors to manage their appointments: viewing, completing, and recording results."),
                        new Tag().name("Common Appointment Management")
                                .description("Endpoints shared between patients and doctors, e.g. checking availability.")
                ));
    }
}
