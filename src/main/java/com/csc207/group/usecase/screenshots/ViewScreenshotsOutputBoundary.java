package com.csc207.group.usecase.screenshots;

public interface ViewScreenshotsOutputBoundary {

    void present(ViewScreenshotsResponseModel responseModel);

    void presentFailure(String message);
}
