document.addEventListener("DOMContentLoaded", function () {
    const feedbackField = document.querySelector("#feedback");
    const historico = document.querySelector("#historico");

    if (feedbackField) {
        feedbackField.addEventListener("load", function () {
            const feedback = feedbackField.textContent;

            if (feedback) {
                if (historico.children.length >= 5) {
                    historico.removeChild(historico.firstChild);
                }

                createHistoricoItem(historico, feedback);
            }
        });
    }

    function createHistoricoItem(container, feedback) {
        const div = document.createElement("div");
        div.classList.add("historicoItem");
        const p = document.createElement("p");
        p.textContent = feedback;
        div.appendChild(p);
        container.appendChild(div);
    }
});
