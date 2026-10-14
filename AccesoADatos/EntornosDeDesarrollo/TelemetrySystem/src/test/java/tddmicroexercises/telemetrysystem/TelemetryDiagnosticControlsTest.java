package tddmicroexercises.telemetrysystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelemetryDiagnosticControlsTest {

    private TelemetryClient telemetryClient;
    private TelemetryDiagnosticControls controls;

    @BeforeEach
    void preparar() {
        telemetryClient = mock(TelemetryClient.class);
        controls = new TelemetryDiagnosticControls(telemetryClient);
    }

    @Test
    void deberiaEnviarMensajeDiagnosticoYRecibirRespuesta()
            throws Exception {

        String respuesta = "Telemetría OK";

        when(telemetryClient.getOnlineStatus())
                .thenReturn(false, true, true);

        when(telemetryClient.receive()).thenReturn(respuesta);

        controls.checkTransmission();

        verify(telemetryClient).disconnect();
        verify(telemetryClient).connect("*111#");
        verify(telemetryClient)
                .send(TelemetryClient.DIAGNOSTIC_MESSAGE);
        verify(telemetryClient).receive();

        assertEquals(respuesta, controls.getDiagnosticInfo());
    }

    @Test
    void deberiaReintentarLaConexionCuandoFallaElPrimerIntento()
            throws Exception {

        when(telemetryClient.getOnlineStatus())
                .thenReturn(false, false, true, true);

        when(telemetryClient.receive())
                .thenReturn("Telemetría OK");

        controls.checkTransmission();

        verify(telemetryClient, times(2))
                .connect("*111#");

        verify(telemetryClient)
                .send(TelemetryClient.DIAGNOSTIC_MESSAGE);

        assertEquals("Telemetría OK", controls.getDiagnosticInfo());
    }

    @Test
    void deberiaLanzarUnaExcepcionCuandoLaConexionFallaTresVeces() {

        when(telemetryClient.getOnlineStatus())
                .thenReturn(false);

        Exception excepcion = assertThrows(
                Exception.class,
                () -> controls.checkTransmission()
        );

        assertEquals("Unable to connect.", excepcion.getMessage());

        verify(telemetryClient, times(3))
                .connect("*111#");

        verify(telemetryClient, never()).send(anyString());
        verify(telemetryClient, never()).receive();
    }

    @Test
    void deberiaBorrarLaInformacionDiagnosticaAnteriorAntesDeComprobar()
            throws Exception {

        controls.setDiagnosticInfo("Información anterior");

        when(telemetryClient.getOnlineStatus())
                .thenReturn(false, true, true);

        when(telemetryClient.receive())
                .thenReturn("Información nueva");

        controls.checkTransmission();

        assertEquals("Información nueva", controls.getDiagnosticInfo());
    }
}