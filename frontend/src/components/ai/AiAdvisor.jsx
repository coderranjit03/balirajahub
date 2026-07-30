import { useState } from "react";
import api from "../../api/axios";

export default function AiAdvisor() {
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [loading, setLoading] = useState(false);

  const askAi = async () => {
    if (!question.trim()) return;

    setLoading(true);

    try {
      const response = await api.post(
        "/api/ai-advisory/ask",
        {
          question,
        }
      );

      setAnswer(response.data.data.answer);
    } catch (error) {
      setAnswer("Unable to get AI advice right now.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="rounded-[2rem] bg-white/80 backdrop-blur-md p-6 shadow-xl border border-white/40">
      <h3 className="text-xl font-bold text-emerald-700 mb-4">
        🤖 AI Farming Advisor
      </h3>

      <textarea
        value={question}
        onChange={(e) => setQuestion(e.target.value)}
        placeholder="Ask about crops, pests, irrigation, fertilizer, or weather..."
        className="w-full rounded-2xl border border-emerald-200 p-4 focus:outline-none focus:ring-2 focus:ring-emerald-500"
        rows={4}
      />

      <button
        onClick={askAi}
        disabled={loading}
        className="mt-4 rounded-2xl bg-emerald-600 px-5 py-3 text-white font-semibold transition hover:bg-emerald-700 disabled:opacity-70"
      >
        {loading ? "Thinking..." : "Ask AI 🌾"}
      </button>

      {answer && (
        <div className="mt-5 rounded-2xl bg-emerald-50 border border-emerald-100 p-4">
          <p className="text-sm font-semibold text-emerald-700 mb-2">
            AI Suggestion
          </p>

          <p className="text-slate-700 whitespace-pre-line">
            {answer}
          </p>
        </div>
      )}
    </div>
  );
}
