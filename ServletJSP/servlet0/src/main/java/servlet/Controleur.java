package servlet;

import facade.GestionQCM;
import facade.QCMImpl;
import modele.QuestionReponse;
import modele.Questionnaire;
import modele.exceptions.InformationsSaisiesIncoherentesException;
import modele.exceptions.QuestionnaireEnCoursNonTermineException;
import modele.exceptions.UtilisateurDejaConnecteException;
import modele.exceptions.ValidationQuestionnaireException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

/**
 * Created by Fred on 15/09/2016.
 */
public class Controleur extends HttpServlet {

    public final static String HOME="home";
    public final static String MENU="menu";
    public final static String GOTOQCM="gotoQCM";
    public final static String GOTOCORR="gotoCorrection";
    public final static String GOTOQUEST="gotoQuestion";
    public final static String GOTOCONS="gotoConsultation";
    public final static String RES="resultat";
    public final static String CONNEXION = "connexion";
    public final static String DECONNEXION = "deconnexion";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // récupération du modèle enregistré en tant que variable d'application
        GestionQCM facade = (GestionQCM) this.getServletContext().getAttribute("facade");

        if (facade == null)
        {
            facade = new QCMImpl();
            // Enregistrement du modèle en tant que variable d'application nommée "facade"
            this.getServletContext().setAttribute("facade", facade);
        }

        String action = request.getParameter("action");
        String target = "/";
        if(HOME.equals(action))
        {
            target = "/WEB-INF/connexion.jsp";
        }

        if(DECONNEXION.equals(action))
        {
            String login = (String) request.getSession().getAttribute("login");
            facade.deconnexion(login);
            target = "/WEB-INF/connexion.jsp";
        }

        if(MENU.equals(action))
        {
            target = "/WEB-INF/menu.jsp";
        }

        if(CONNEXION.equals(action))
        {
            String login = request.getParameter("login");
            String pwd = request.getParameter("pwd");

            try
            {
                facade.connexion(login,pwd);
                request.getSession().setAttribute("login", login);
                target = "/WEB-INF/menu.jsp";
            }
            catch (UtilisateurDejaConnecteException e)
            {
                String erreur = "Utilisateur " + login + " déjà connecté !";
                request.setAttribute("erreur",erreur);
                target = "/WEB-INF/connexion.jsp";
            }
            catch (InformationsSaisiesIncoherentesException e)
            {
                String erreur = "Le couple saisi login/pwd n'est pas connu de l'application !";
                request.setAttribute("erreur",erreur);
                target = "/WEB-INF/connexion.jsp";
            }
        }

        if(GOTOQCM.equals(action))
        {
            Collection<Questionnaire> questionnaires =
                    facade.getListQuestionnairesNonFaits((String)request.getSession().getAttribute("login"));
            request.setAttribute("questionnaires", questionnaires);
            target = "/WEB-INF/choixQuestionnaireAFaire.jsp";
        }

        if(GOTOCORR.equals(action))
        {
            Collection<Questionnaire> questionnaires =
                    facade.getListQuestionnairesFaits((String) request.getSession().getAttribute("login"));
            request.setAttribute("questionnaires", questionnaires);
            target = "/WEB-INF/choixQuestionnaireFaits.jsp";
        }

        if(GOTOQUEST.equals(action))
        {

            /**
             * Ici on cherche à valider la question
             * Si l'utilisateur vient sur cette page pour la première fois il ne se passe rien.
             * Sinon il valide la question avec la réponse donnée
             */
            try {
                facade.validerQuestion((String) request.getSession().getAttribute("login"), Integer.parseInt(request.getParameter("question")), (String) request.getParameter("choixReponse"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            /**
             * Ici on récupère l'id du questionnaire auquel l'utilisateur répond.
             * On applique ensuite le setAttribute
             */
            int idQuestionnaire = Integer.parseInt(request.getParameter("choixQuestion"));
            request.setAttribute("idQuestionnaire", idQuestionnaire);

            /**
             * Ici on informe le système que l'utilisateur a choisi le Questionnaire ayant l'id idQuestionnaire.
             * Cela signifie que tant qu'il n'a pas fini ce QCM il ne peut en choisir un autre.
             */
            try {
                facade.choixQuestionnaire((String)request.getSession().getAttribute("login"), Integer.parseInt(request.getParameter("choixQuestion")));
            } catch (QuestionnaireEnCoursNonTermineException e) {
                e.printStackTrace();
            }


            /**
             * On test maintenant de voir il y a encore au moins une question à répondre.
             * Si c'est le cas on affecte la valeur de la question courante pour récupérer ses informations par la suite
             */
            if(facade.hasNext((String) request.getSession().getAttribute("login"), Integer.parseInt(request.getParameter("choixQuestion")))) {
                QuestionReponse questionCourante =
                        facade.next((String) request.getSession().getAttribute("login"), Integer.parseInt(request.getParameter("choixQuestion")));
                request.setAttribute("questionCourante", questionCourante);
                target = "/WEB-INF/realiserQCM.jsp";
            }
            /**
             * Si il ne reste plus de questions, on valide le questionnaire et on envoie le score
             */
            else
            {
                try
                {
                    double score = facade.validerQuestionnaire((String) request.getSession().getAttribute("login"));
                    request.setAttribute("score", score);
                    target = "/WEB-INF/resultat.jsp";
                }
                catch (ValidationQuestionnaireException e)
                {
                    e.printStackTrace();
                }
            }

        }

        if(GOTOCONS.equals(action))
        {
            int idQuestionnaire = Integer.parseInt(request.getParameter("choixQuestion"));
            Collection<Questionnaire> questionnaires = facade.getListQuestionnairesFaits((String) request.getSession().getAttribute("login"));
            for(Questionnaire q : questionnaires)
            {
                if(q.getIdQuestionnaire() == idQuestionnaire)
                {
                    request.setAttribute("questionnaire", q);
                }
            }
            target = "/WEB-INF/correction.jsp";
        }

        if(RES.equals(action))
        {
            target = "/WEB-INF/resultat.jsp";
        }

        getServletContext().getRequestDispatcher(target).forward(request,response);
    }

}
