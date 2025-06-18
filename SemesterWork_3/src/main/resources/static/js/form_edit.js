document.addEventListener('DOMContentLoaded', function() {
    console.log('Initializing form editor...');
    const deletedFieldsIds = new Set();
    const addFieldButton = document.getElementById('addField');
    const formBuilder = document.getElementById('formBuilder');
    const fieldsContainer = document.getElementById('fieldsContainer');

    document.querySelectorAll('.field-container').forEach(fieldContainer => {
        setupFieldEventListeners(fieldContainer);
    });

    function setupFieldEventListeners(fieldContainer) {

        fieldContainer.querySelector('.add-option').addEventListener('click', (e) => {
            addOption(e.target.closest('.field-container'));
        });

        fieldContainer.querySelector('.remove-field').addEventListener('click', function() {
            const fieldId = fieldContainer.querySelector('.field-id')?.value;

            if (fieldId) {
                deletedFieldsIds.add(fieldId);
                fieldContainer.remove();
            }
        });

        fieldContainer.querySelectorAll('.remove-option').forEach(button => {
            button.addEventListener('click', function() {
                const optionItem = this.closest('.input-group');
                const optionsList = optionItem.parentNode;
                if (optionsList.querySelectorAll('.input-group').length > 1) {
                    optionItem.remove();
                }
            });
        });
    }

    function hasFieldChanged(container) {
        const originalQuestion = container.querySelector('.original-question').value;
        const currentQuestion = container.querySelector('.question').value;

        const originalRequired = container.querySelector('.original-is-required').value === 'true';
        const currentRequired = container.querySelector('.is-required').checked;

        let originalOptions = [];
        try {
            originalOptions = JSON.parse(container.querySelector('.original-options').value || '[]');
        } catch (e) {
            console.error('Error parsing original options:', e);
        }

        const currentOptions = [];
        container.querySelectorAll('.option-value').forEach(option => {
            if (option.value.trim()) {
                currentOptions.push(option.value.trim());
            }
        });

        return originalQuestion !== currentQuestion ||
            originalRequired !== currentRequired ||
            JSON.stringify(originalOptions) !== JSON.stringify(currentOptions);
    }

    if (addFieldButton && fieldsContainer) {
        addFieldButton.addEventListener('click', function() {
            const newField = createField();
            fieldsContainer.appendChild(newField);
            newField.scrollIntoView({ behavior: 'smooth' });
        });
    }

    if (formBuilder) {
        formBuilder.addEventListener('submit', function(e) {
            e.preventDefault();

            if (!validateForm()) {
                return;
            }

            const formData = new FormData(formBuilder);
            const formFields = [];

            document.querySelectorAll('.field-container').forEach((container, index) => {
                const fieldId = container.querySelector('.field-id')?.value;
                const isModified = !fieldId || hasFieldChanged(container);

                const fieldData = {
                    id: fieldId ? Number(fieldId.replace(/\s+/g, '')) : null,
                    fieldType: container.querySelector('.field-type').value,
                    question: container.querySelector('.question').value,
                    isRequired: container.querySelector('.is-required').checked,
                    position: index + 1,
                    deleted: false
                };

                if (!isModified && fieldId && (parseInt(container.querySelector('.original-position').value) === fieldData.position)){
                    return
                }

                if (fieldData.fieldType === FIELD_TYPES.SINGLE_CHOICE ||
                    fieldData.fieldType === FIELD_TYPES.MULTIPLE_CHOICE) {
                    const options = [];
                    container.querySelectorAll('.option-value').forEach(option => {
                        if (option.value.trim()) {
                            options.push(option.value.trim());
                        }
                    });
                    fieldData.options = options.length > 0 ? JSON.stringify(options) : null;
                }

                formFields.push(fieldData);
            });

            deletedFieldsIds.forEach(id => {
                formFields.push({
                    id: Number(id.replace(/\s+/g, '')),
                    fieldType: 'deleted',
                    question: 'deleted',
                    isRequired: false,
                    position: Number(1000),
                    deleted: true
                });
            });

            submitFormData(formBuilder.action, formFields);
        });
    }
});
