package net.minecraft.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import net.minecraft.server.MinecraftServer;

import java.util.regex.Pattern; // CraftBukkit

class LogFormatter extends Formatter
{
    private SimpleDateFormat field_98228_b;

    final LogAgent field_98229_a;
    // CraftBukkit start - add color stripping
    private Pattern pattern = Pattern.compile("\\x1B\\[([0-9]{1,2}(;[0-9]{1,2})?)?[m|K]");
    private boolean strip = false;
    // CraftBukkit end

    private LogFormatter(LogAgent p_i11034_1_)
    {
        this.field_98229_a = p_i11034_1_;
        this.field_98228_b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.strip = MinecraftServer.func_71276_C().options.has("log-strip-color"); // CraftBukkit
    }

    public String format(LogRecord p_format_1_)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(this.field_98228_b.format(Long.valueOf(p_format_1_.getMillis())));

        if (LogAgent.func_98237_a(this.field_98229_a) != null)
        {
            stringbuilder.append(LogAgent.func_98237_a(this.field_98229_a));
        }

        stringbuilder.append(" [").append(p_format_1_.getLevel().getName()).append("] ");
        stringbuilder.append(this.formatMessage(p_format_1_));
        stringbuilder.append('\n');
        Throwable throwable = p_format_1_.getThrown();

        if (throwable != null)
        {
            StringWriter stringwriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringwriter));
            stringbuilder.append(stringwriter.toString());
        }

        // CraftBukkit start - handle stripping color
        if (this.strip)
        {
            return this.pattern.matcher(stringbuilder.toString()).replaceAll("");
        }
        else
        {
            return stringbuilder.toString();
        }

        // CraftBukkit end
    }

    LogFormatter(LogAgent p_i11035_1_, LogAgentINNER1 p_i11035_2_)
    {
        this(p_i11035_1_);
    }
}
