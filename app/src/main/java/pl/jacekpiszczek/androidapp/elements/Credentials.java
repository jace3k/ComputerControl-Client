package pl.jacekpiszczek.androidapp.elements;

import io.grpc.*;

import java.util.concurrent.Executor;

public class Credentials implements CallCredentials {
    private final String pin;

    Credentials(String pin) {
        this.pin = pin;
    }

    @Override
    public void applyRequestMetadata(MethodDescriptor<?, ?> method, Attributes attrs, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                Metadata.Key<String> pinKey = Metadata.Key.of("pin", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(pinKey, pin);
                applier.apply(headers);
            } catch (Throwable e) {
                applier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {

    }
}
