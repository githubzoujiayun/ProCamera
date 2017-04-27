package com.eighteengray.procameralibrary.camera;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.view.Surface;
import android.view.WindowManager;

import static android.R.attr.rotation;
import static com.eighteengray.procameralibrary.camera.Camera2TextureView.ORIENTATIONS;


public class CaptureRequestFactory
{
    //创建预览请求，后面的setXXX方法是根据不同情况设置预览参数
    public static CaptureRequest.Builder createPreviewBuilder(CameraDevice cameraDevice, Surface surface) throws CameraAccessException
    {
        CaptureRequest.Builder previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        previewBuilder.addTarget(surface);
        return previewBuilder;
    }

    //设置预览-自动聚焦模式
    public static void setPreviewBuilderPreview(CaptureRequest.Builder previewBuilder) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
//        previewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
    }

    //设置预览-锁定焦点
    public static void setPreviewBuilderLockfocus(CaptureRequest.Builder previewBuilder) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
    }

    //设置预览-解除锁定焦点
    public static void setPreviewBuilderUnlockfocus(CaptureRequest.Builder previewBuilder) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
//        previewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

    }

    //设置预览-聚焦区域
    public static void setPreviewBuilderFocusRegion(CaptureRequest.Builder previewBuilder, MeteringRectangle[] meteringRectangleArr) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
        previewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
        previewBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, meteringRectangleArr);
    }

    //设置预览-闪光灯模式
    public static CaptureRequest setPreviewBuilderFlash(CaptureRequest.Builder previewBuilder, int flashMode) throws CameraAccessException
    {
        previewBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
        switch (flashMode)
        {
            case Constants.FLASH_AUTO:
                previewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH);
                previewBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_SINGLE);
                break;

            case Constants.FLASH_ON:
                previewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH);
                previewBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_STATE_READY);
                break;

            case Constants.FLASH_OFF:
                previewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
                previewBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                break;

            case Constants.FLASH_FLARE:
                previewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
                previewBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                break;
        }
        return previewBuilder.build();
    }



    //创建拍照请求
    public static CaptureRequest.Builder createCaptureStillBuilder(CameraDevice cameraDevice, Surface surface) throws CameraAccessException
    {
        CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
        captureBuilder.addTarget(surface);
        return captureBuilder;
    }

    //设置拍照模式-拍静态图片
    public static void setCaptureBuilderStill(CaptureRequest.Builder captureBuilder, WindowManager windowManager) throws CameraAccessException
    {
        captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
//        captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

        int rotation = windowManager.getDefaultDisplay().getRotation();
        captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
    }

    //设置拍照模式-连续拍摄
    public static void setCaptureBuilderPrecapture(CaptureRequest.Builder captureBuilder) throws CameraAccessException
    {
        captureBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);
    }


    //创建录像请求
    public static CaptureRequest.Builder createRecordBuilder(CameraDevice cameraDevice, Surface surface) throws CameraAccessException
    {
        CaptureRequest.Builder recordBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
        recordBuilder.addTarget(surface);
        return recordBuilder;
    }


}