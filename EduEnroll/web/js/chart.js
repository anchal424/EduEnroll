var ctx = document.getElementById('progressChart').getContext('2d');

new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ["Java", "Web", "Python"],
        datasets: [{
            label: 'Progress %',
            data: [60, 80, 40],
            backgroundColor: '#8E1FA3'
        }]
    }
});