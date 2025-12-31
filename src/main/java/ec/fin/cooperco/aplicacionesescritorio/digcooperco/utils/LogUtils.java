package ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {

    public static void setupLog(final LogTypeEnum type, final MessageCodeEnum message, final String value, Exception ex) {
        switch (type) {
            case error:
                log.error(LogUtils.getStaticMessage(message, value), ex);
                break;
            case info:
                log.info(LogUtils.getStaticMessage(message, value), ex);
                break;
            case warn:
                log.warn(LogUtils.getStaticMessage(message, value), ex);
                break;
        }
    }

    private static String getStaticMessage(MessageCodeEnum type, String value) {
        switch (type) {
            case start_session:
                return MessageUtils.replaceUsername(value, MessageUtils.startSessionMessageStatic).toString();
            case user_not_exist:
                return MessageUtils.replaceUsername(value, MessageUtils.userNotExistMessageStatic).toString();
            case update_version:
                return MessageUtils.replaceVersion(value, MessageUtils.updateVersionMessageStatic).toString();
            case user_find:
                return MessageUtils.replaceUsername(value, MessageUtils.userFindMessageStatic).toString();
            case user_login:
                return MessageUtils.replaceUsername(value, MessageUtils.userLoginMessageStatic).toString();
            case user_login_error:
                return MessageUtils.replaceUsername(value, MessageUtils.userLoginErrorMessageStatic).toString();
            case user_login_password_error:
                return MessageUtils.replaceUsername(value, MessageUtils.userLoginPasswordErrorMessageStatic).toString();
            case file_general_error:
                return MessageUtils.fileGeneralErrorMessageStatic;
            default:
                return MessageUtils.configInfoMessageStatic;
        }
    }

}
