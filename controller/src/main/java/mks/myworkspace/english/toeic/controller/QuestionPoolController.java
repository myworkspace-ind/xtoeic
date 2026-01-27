package mks.myworkspace.english.toeic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sakaiproject.tool.assessment.data.ifc.questionpool.QuestionPoolDataIfc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.controller.model.TreeNode;


/**
 * @author thach
 * Provide API data for Tree of Question Pools.
 * @see TreeJS
 *
 */
@Controller
@Slf4j
public class QuestionPoolController extends BaseController {

    /**
     * Process ajax request from Tree of Question Pools from client.
     * @return json format of Node
     * @see https://www.jstree.com/docs/json/
     */
    @RequestMapping(value = "/getNodeRoot", method = RequestMethod.GET)
    @ResponseBody
    public String getNodeRoot() {
        TreeNode rootNode = new TreeNode();
        
        rootNode.setText("QuestionPools");
        rootNode.setId(0L);
        rootNode.setOpened(true);
        
        List<QuestionPoolDataIfc> questionPools = getSakaiProxy().getPools();

        List<TreeNode> listPoolL1 = buildTreeNode(questionPools);

        if (listPoolL1 != null) {
            log.debug("listPoolL1 size:" + listPoolL1.size());
            for (QuestionPoolDataIfc pool : questionPools) {
                log.debug("Pool Title:" + pool.getTitle());
            }
        }
        
        rootNode.setChildren(listPoolL1);

        return new Gson().toJson(rootNode);

    }
    
    /**
     * Process ajax request from Tree of Question Pools to load sub Pools.
     * 
     * @param id Identifier of the Question Pool.
     * @return json data of sub pools.
     */
    @RequestMapping(value = "/getNodeChildren", method = RequestMethod.GET)
    @ResponseBody
    public String retrieveSubPools(@RequestParam("id") Long poolId) {
        TreeNode rootNode = new TreeNode();

        // Get Sub Pools
        List<QuestionPoolDataIfc> questionPools = getSakaiProxy().getPools(poolId);
        
        log.debug("Number subpools of '" + poolId + ": " + ((questionPools != null) ? questionPools.size() : -1));

        List<TreeNode> listPoolL1 = buildTreeNode(questionPools);
        
        rootNode.setChildren(listPoolL1);

        return new Gson().toJson(rootNode);
    }
    
    /**
     * Convert from list of entity Question Pools into list of TreeNode to display in the client.
     * @param questionPools
     * @return
     */
    private List<TreeNode> buildTreeNode(List<QuestionPoolDataIfc> questionPools) {
        List<TreeNode> listPools = new ArrayList<TreeNode>();
        TreeNode node;

        List<QuestionPoolDataIfc> listSubPools;
        List<TreeNode> listSubTreeNode;
        for (QuestionPoolDataIfc questionPool : questionPools) {
            log.info("questionPool.getParentPoolId()=" + questionPool.getParentPoolId());

            node = convert2TreeNode(questionPool);
            
            // Build recursively nodes
            listSubPools = getSakaiProxy().getPools(questionPool.getQuestionPoolId());
            listSubTreeNode = buildTreeNode(listSubPools);
            node.setChildren(listSubTreeNode);
            
            listPools.add(node);

        }
        
        return listPools;
    }

    /**
     * Convert data from QuestionPoolDataIfc to format of JSTree.
     * @param poolData maybe contains all Question Pools which includes sub pools
     * @return
     */
    private TreeNode convert2TreeNode(QuestionPoolDataIfc poolData) {
        TreeNode treeNode;
        
        if (poolData == null) {
            treeNode = null;
        } else {
            treeNode = new TreeNode();
            treeNode.setId(poolData.getQuestionPoolId());
            treeNode.setText(poolData.getTitle());
            treeNode.setParent(poolData.getParentPoolId());
            
            Map<String, String> a_attr = new HashMap<String, String>();
            a_attr.put("title", poolData.getTitle());
            a_attr.put("description", poolData.getDescription());
            
            treeNode.setA_attr(a_attr);
            
//            // Check having children or not.
//            for (QuestionPoolDataIfc poolItem : questionPools) {
//                if (poolItem.getParentPoolId() == treeNode.getId()) {
//                    treeNode.setOpened(true);
//                    treeNode.setHasChildren();
//                }
//            }
        }
        
        return treeNode;
    }

}
