package eu.libal.intrographs.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class ListenableFieldTest {

    ListenableField<String> listenableField;

    @Before
    public void setup() {
        listenableField = new ListenableField<>();
    }

    @Test
    public void shouldReturnNullIfNoValuePassedAtConstruction() {
        assertTrue(listenableField.getValue() == null);
    }

    @Test
    public void shouldSetValueOnConstructionIfPassed() {
        ListenableField<String> anotherListenable = new ListenableField<>("foo");

        assertThat(anotherListenable.getValue(), is("foo"));
    }

    @Test
    public void shouldEmitUpdateEventEveryTimeValueIsUpdated() {
        INotifiable notifiable = Mockito.mock(INotifiable.class);
        listenableField.subscribe("update", notifiable);

        listenableField.setValue("bar");

        verify(notifiable).call("bar");

        listenableField.setValue("baz");

        verify(notifiable).call("baz");
    }
}