package pl.rspective.voucherify.android.client.module;

import java.util.concurrent.Executor;

import pl.rspective.voucherify.android.client.api.VoucherifyApi;
import pl.rspective.voucherify.android.client.callback.VoucherifyCallback;
import pl.rspective.voucherify.android.client.exception.VoucherifyError;
import pl.rspective.voucherify.android.client.model.VoucherResponse;
import pl.rspective.voucherify.android.client.utils.RxUtils;
import retrofit.RetrofitError;
import rx.Observable;

abstract class BaseModule<T> extends AbsModule<BaseModule.ExtAsync, BaseModule.ExtRxJava> {

    private static final String CHANNEL = "android";

    private final String trackingId;

    public BaseModule(VoucherifyApi api, Executor executor, String trackingId) {
        super(api, executor);

        this.trackingId = trackingId;
    }

    @Override
    ExtAsync createAsyncExtension() {
        return new ExtAsync();
    }

    @Override
    ExtRxJava createRxJavaExtension() {
        return new ExtRxJava();
    }

    public T validate(String code) {
        return validate(code, null);
    }

    public T validate(String code, Integer amount) {
        return (T) api.validateVoucher(code, trackingId, amount, CHANNEL);
    }

    /**
     * Base Async extension.
     */
    public class ExtAsync extends Async {

        public void validate(String code, VoucherifyCallback<VoucherResponse, VoucherifyError> callback) {
            RxUtils.subscribe(executor, rx().validate(code), callback);
        }

        public void validate(String code, Integer amount, VoucherifyCallback<VoucherResponse, VoucherifyError> callback) {
            RxUtils.subscribe(executor, rx().validate(code, amount), callback);
        }

    }

    /**
     * Base RxJava extension.
     */
    public class ExtRxJava extends Rx {

        public Observable<T> validate(final String code) {
            return RxUtils.defer(new RxUtils.DefFunc<T>() {
                @Override
                public T method() {
                    return BaseModule.this.validate(code);
                }
            });
        }

        public Observable<T> validate(final String code, final Integer amount) {
            return RxUtils.defer(new RxUtils.DefFunc<T>() {
                @Override
                public T method() {
                    return BaseModule.this.validate(code, amount);
                }
            });
        }

    }

    public class Rx {}

    public class Async {}

}