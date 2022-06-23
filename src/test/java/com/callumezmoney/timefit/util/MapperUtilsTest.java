package com.callumezmoney.timefit.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MapperUtilsTest {
    @Mock
    Environment environment;
    private String uri = "/api/exercise/4";
    private String propertyName = "callumezmoney.app.webapiprefix.exercise";
    private String propertyValue = "/api/exercise";

    @BeforeEach
    void setUp() {
        doReturn(propertyValue).when(environment).getProperty(propertyName);
    }

    @Test
    void testGetIdFromURI() {
        Long id = getIdFromURI(uri, environment, "exercise");
        assertEquals(id, 4L);
    }
}