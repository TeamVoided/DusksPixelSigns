package org.teamvoided.dusks_pixel_signs.client.config

import me.fzzyhmstrs.fzzy_config.entry.EntryValidator
import me.fzzyhmstrs.fzzy_config.screen.widget.LayoutClickableWidget
import me.fzzyhmstrs.fzzy_config.screen.widget.LayoutWidget
import me.fzzyhmstrs.fzzy_config.screen.widget.ValidationBackedNumberFieldWidget
import me.fzzyhmstrs.fzzy_config.util.FcText.translate
import me.fzzyhmstrs.fzzy_config.util.ValidationResult
import me.fzzyhmstrs.fzzy_config.validation.ValidatedField
import me.fzzyhmstrs.fzzy_config.validation.misc.ChoiceValidator
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarratedElementType
import net.minecraft.world.phys.Vec3
import net.peanuuutz.tomlkt.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier

class ValidatedVec3(storedValue: Vec3, defaultValue: Vec3 = storedValue, val minValue: Double, val maxValue: Double) :
    ValidatedField<Vec3>(storedValue, defaultValue) {

    constructor(defaultValue: Vec3, maxValue: Double = Double.MAX_VALUE, minValue: Double = Double.MIN_VALUE)
            : this(defaultValue, defaultValue, minValue, maxValue)

    constructor(
        x: Double, y: Double, z: Double,
        maxValue: Double = Double.MAX_VALUE, minValue: Double = Double.MIN_VALUE,
    ) : this(Vec3(x, y, z), maxValue, minValue)

    override fun deserialize(
        toml: TomlElement,
        fieldName: String,
    ): ValidationResult<Vec3> {
        return try {
            val array = toml.asTomlArray().map { it.asTomlLiteral().toDouble() }
            if (array.size != 3) {
                return ValidationResult.error(
                    storedValue,
                    ValidationResult.Errors.INVALID,
                    "Vec3 array doest have 3 elements [$fieldName]: $array."
                )
            }
            ValidationResult.success(Vec3(array[0], array[1], array[2]))
        } catch (e: Throwable) {
            ValidationResult.error(
                storedValue,
                ValidationResult.Errors.DESERIALIZATION,
                "Exception deserializing vec3 [$fieldName]",
                e
            )
        }
    }

    override fun serialize(input: Vec3): ValidationResult<TomlElement> {
        return ValidationResult.success(TomlArray(TomlLiteral(input.x), TomlLiteral(input.y), TomlLiteral(input.z)))
    }

    override fun instanceEntry(): ValidatedField<Vec3> {
        return copyProvidersTo(ValidatedVec3(storedValue, defaultValue, minValue, maxValue))
    }

    override fun isValidEntry(input: Any?): Boolean {
        return input is Vec3 && validateEntry(input, EntryValidator.ValidationType.STRONG).isValid()
    }

    override fun validateEntry(input: Vec3, type: EntryValidator.ValidationType): ValidationResult<Vec3> {
        return input.validatedPart(input.x, "x")
            ?: input.validatedPart(input.y, "y")
            ?: input.validatedPart(input.z, "z")
            ?: ValidationResult.success(input)
    }

    fun Vec3.validatedPart(field: Double, fieldName: String): ValidationResult<Vec3>? {
        if (field < minValue)
            return ValidationResult.error(
                this,
                ValidationResult.Errors.OUT_OF_BOUNDS,
                "Validated vec3 field ($fieldName) [${this}] below the valid range [${minValue}] to [${maxValue}]"
            )
        else if (field > maxValue)
            return ValidationResult.error(
                this,
                ValidationResult.Errors.OUT_OF_BOUNDS,
                "Validated vec3 field ($fieldName) [${this}] above the valid range [${minValue}] to [${maxValue}]"
            )

        return null
    }

    fun validatedNum(num: Double, fieldName: String): ValidationResult<Double> {
        if (num < minValue)
            return ValidationResult.error(
                num,
                ValidationResult.Errors.OUT_OF_BOUNDS,
                "Validated vec3 field ($fieldName) [${this}] below the valid range [${minValue}] to [${maxValue}]"
            )
        else if (num > maxValue)
            return ValidationResult.error(
                num,
                ValidationResult.Errors.OUT_OF_BOUNDS,
                "Validated vec3 field ($fieldName) [${this}] above the valid range [${minValue}] to [${maxValue}]"
            )

        return ValidationResult.success(num)
    }

    @Suppress("UnstableApiUsage")
    override fun widgetEntry(choicePredicate: ChoiceValidator<Vec3>): AbstractWidget {
        val layout = LayoutWidget.builder().paddingBoth(0).spacingBoth(0).clampWidth(110).build()
        layout.numberWidget("x", { storedValue.x }) { setAndUpdate(Vec3(it, storedValue.y, storedValue.z)) }
        layout.numberWidget("y", { storedValue.y }) { setAndUpdate(Vec3(storedValue.x, it, storedValue.z)) }
        layout.numberWidget("z", { storedValue.z }) { setAndUpdate(Vec3(storedValue.x, storedValue.y, it)) }
        return LayoutClickableWidget(0, 0, 110, 20 * 3, layout).withNarrationAppender { builder ->
            builder.add(
                NarratedElementType.TITLE,
                "fc.validated_field.current".translate(this.translationKey()).append(". ")
            )
        }
    }

    fun LayoutWidget.numberWidget(name: String, value: () -> Double, valueApplier: (Double) -> Unit) {
        add(
            name,
            NumberButtonTextFieldWidget(
                value,
                ChoiceValidator.any(),
                { validatedNum(it, name) },
                valueApplier
            ),
            LayoutWidget.Position.BELOW,
            LayoutWidget.Position.ALIGN_CENTER
        )
    }

    class NumberButtonTextFieldWidget<T : Number>(
        wrappedValue: Supplier<T>,
        choiceValidator: ChoiceValidator<T>,
        validationProvider: Function<Double, ValidationResult<T>>,
        valueApplier: Consumer<T>,
        width: Int = 110,
        renderStatus: Boolean = true,
        increment: Double = 0.0,
    ) : ValidationBackedNumberFieldWidget<T>(
        width,
        20,
        wrappedValue,
        choiceValidator,
        validationProvider,
        valueApplier,
        renderStatus,
        increment
    )
}