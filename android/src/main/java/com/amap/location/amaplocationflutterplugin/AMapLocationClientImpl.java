package com.amap.location.amaplocationflutterplugin;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.Map;

import io.flutter.plugin.common.EventChannel;

/**
 * @author whm
 * @date 2020-04-16 15:49
 * @mail hongming.whm@alibaba-inc.com
 */
public class AMapLocationClientImpl implements AMapLocationListener {

    private Context mContext;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private AMapLocationClient locationClient = null;
    private EventChannel.EventSink mEventSink;

    private String mPluginKey;

    public AMapLocationClientImpl(Context context, String pluginKey, EventChannel.EventSink eventSink) {
        mContext = context;
        mPluginKey = pluginKey;
        mEventSink = eventSink;
        if(null == locationClient) {
            locationClient = new AMapLocationClient(context);
        }
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        if (null == locationClient) {
            locationClient = new AMapLocationClient(mContext);
        }
        if (null != locationOption) {
            locationClient.setLocationOption(locationOption);
        }
        locationClient.setLocationListener(this);
        locationClient.startLocation();
    }


    /**
     * 停止定位
     */
    public void stopLocation() {
        if (null != locationClient) {
            locationClient.stopLocation();
            locationClient.onDestroy();
            locationClient = null;
        }
    }

    public void destroy() {
        if(null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
        }
    }
    /**
     * 定位回调
     *
     * @param location
     */
    @Override
    public void onLocationChanged(AMapLocation location) {
        if (null == mEventSink) {
            return;
        }
        Map<String, Object> result = Utils.buildLocationResultMap(location);
        result.put("pluginKey", mPluginKey);
        mEventSink.success(result);
    }


    /**
     * 设置定位参数
     *
     * @param optionMap
     */
    public void setLocationOption(Map optionMap) {
        if (null == locationOption) {
            locationOption = new AMapLocationClientOption();
        }

        if (optionMap.containsKey("locationInterval")) {
            locationOption.setInterval(((Integer) optionMap.get("locationInterval")).longValue());
        }

        if (optionMap.containsKey("needAddress")) {
            locationOption.setNeedAddress((boolean) optionMap.get("needAddress"));
        }

        if (optionMap.containsKey("locationMode")) {
            // 与 lib/amap_location_option.dart 中 AMapLocationMode 的声明顺序一一对应，
            // 不能直接使用 values()[index]：SDK 自身的枚举声明顺序与之不同。
            switch ((int) optionMap.get("locationMode")) {
                case 1:
                    locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
                    break;
                case 2:
                    locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    break;
                case 0:
                default:
                    locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
                    break;
            }
        }

        if (optionMap.containsKey("geoLanguage")) {
            // 与 lib/amap_location_option.dart 中 GeoLanguage 的声明顺序一一对应，
            // 不能直接使用 values()[index]：SDK 自身的枚举声明顺序与之不同。
            switch ((int) optionMap.get("geoLanguage")) {
                case 1:
                    locationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.ZH);
                    break;
                case 2:
                    locationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.EN);
                    break;
                case 0:
                default:
                    locationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);
                    break;
            }
        }

        if (optionMap.containsKey("onceLocation")) {
            locationOption.setOnceLocation((boolean) optionMap.get("onceLocation"));
        }

        if (null != locationClient) {
            locationClient.setLocationOption(locationOption);
        }
    }
}
