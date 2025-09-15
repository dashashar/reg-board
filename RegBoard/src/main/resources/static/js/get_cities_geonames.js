function initCityAutocomplete() {
    const input = document.getElementById('cityInput');
    if (!input) return;

    const suggestions = document.getElementById('suggestions');
    const username = 'dasha';

    suggestions.innerHTML = '';
    suggestions.style.display = 'none';

    input.addEventListener('input', async () => {
        const query = input.value.trim();
        if (query.length < 2) {
            suggestions.style.display = 'none';
            suggestions.innerHTML = '';
            return;
        }

        try {
            const url = 'http://api.geonames.org/searchJSON?name_startsWith=' +
                encodeURIComponent(query) +
                '&lang=ru&maxRows=20&username=' + username +
                '&featureClass=P';

            const response = await fetch(url);
            if (!response.ok) throw new Error('Network response was not ok');

            const data = await response.json();
            suggestions.innerHTML = '';

            if (!data.geonames || data.geonames.length === 0) {
                suggestions.innerHTML = '<div>Ничего не найдено</div>';
                suggestions.style.display = 'block';
                return;
            }

            const uniqueCities = new Set();

            data.geonames.forEach(city => {
                const key = city.name.toLowerCase() + '|' + (city.countryName || '').toLowerCase();
                if (!uniqueCities.has(key)) {
                    uniqueCities.add(key);
                    const div = document.createElement('div');
                    div.textContent = city.name + (city.countryName ? ', ' + city.countryName : '');
                    div.addEventListener('click', () => {
                        input.value = city.name + (city.countryName ? ', ' + city.countryName : '');
                        suggestions.style.display = 'none';
                    });
                    suggestions.appendChild(div);
                }
            });

            suggestions.style.display = 'block';
        } catch (err) {
            console.error('Ошибка при запросе к GeoNames:', err);
            suggestions.innerHTML = '<div>Ошибка загрузки данных</div>';
            suggestions.style.display = 'block';
        }
    });

    document.addEventListener('click', (e) => {
        if (e.target !== input && e.target !== suggestions) {
            suggestions.style.display = 'none';
        }
    });

    input.addEventListener('click', () => {
        if (suggestions.innerHTML !== '') {
            suggestions.style.display = 'block';
        }
    });

    input.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            suggestions.style.display = 'none';
        }
    });
}

document.addEventListener('DOMContentLoaded', initCityAutocomplete);