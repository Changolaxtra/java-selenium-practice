package com.practice.ui.testcases.tc00_visit_home_page;

import com.practice.base.BaseUiTest;
import com.practice.ui.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test 0: Visit Home Page")
class VisitHomePageTest extends BaseUiTest {

    @Test
    @DisplayName("home page loads successfully")
    void visitHomePage() {
        HomePage homePage = new HomePage(driver).open();

        assertThat(homePage.title()).isEqualTo("Automation Exercise");
        assertThat(homePage.isLoaded()).isTrue();
    }
}
