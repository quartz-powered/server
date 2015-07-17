appender("STDOUT", ConsoleAppender) {
    try {
        Class.forName('com.intellij.rt.execution.application.AppMain')
    } catch (ignored) {
        withJansi = true
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %highlight(%-5level) %magenta(%logger{15}) - %msg %n"
    }
}

root(INFO, ["STDOUT"])