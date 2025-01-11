package net.cc.example.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@SuppressWarnings("UnstableApiUsage")

public final class FlightSpeedCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("flyspeed")
                .then(Commands.argument("speed", FloatArgumentType.floatArg(0, 1.0f))
                        .executes(FlightSpeedCommand::run)
                );
    }

    private static int run(CommandContext<CommandSourceStack> ctx) {
        float speed = ctx.getArgument("speed", Float.class);
        CommandSender sender = ctx.getSource().getSender();
        Entity executor = ctx.getSource().getExecutor();

        if (executor instanceof Player player) {
            player.setFlySpeed(speed);

            if (sender == executor) {
                player.sendMessage(Component.text("Successfully set your flight speed to " + speed).color(NamedTextColor.GOLD));
            } else {
                sender.sendMessage(Component.text("Successfully set " + player.getName() + "'s flight speed to " + speed).color(NamedTextColor.GOLD));
                player.sendMessage(Component.text("Your flight speed has been set to " + speed).color(NamedTextColor.GOLD));
            }
        } else {
            sender.sendMessage(Component.text("Consoles can't fly!").color(NamedTextColor.RED));
        }

        return Command.SINGLE_SUCCESS;
    }
}
